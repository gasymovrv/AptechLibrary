package ru.aptech.library.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.aptech.library.BaseTest;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.entities.Book;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaBooks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@Ignore
@RunWith(MockitoJUnitRunner.class) //это делает инит моков вместо MockitoAnnotations.initMocks(this);
public class BookServiceTest extends BaseTest {
    private Book b1 = new Book();
    private Book b2 = new Book();
    private Book b3 = new Book();
    private List<Book> books = new ArrayList<>(Arrays.asList(b1, b2, b3));

    @Mock
    private CommonDAO<Book> bookDAO;

    @InjectMocks
    private BookService bookService; //объект в который будем инжектить моки

    @Before
    public void init() {
//        MockitoAnnotations.initMocks(this); //вместо этого можно написать переда классом @RunWith(MockitoJUnitRunner.class)
        //интерфейс почему-то не инжектится, пришлось так
        bookService.setBookDAO(bookDAO);
        b1.setName("Книга1");
        b2.setName("Книга2");
        b3.setName("Книга3");

        when(bookDAO.find(0L)).thenReturn(b1);
        when(bookDAO.find(1L)).thenReturn(b2);

        when(bookDAO.find(3, 0, SortType.NAME)).thenReturn(books);
        when(bookDAO.find(new SearchCriteriaBooks(), 3, 0, SortType.NAME)).thenReturn(books);
    }


    @Test
    public void find() {
        System.out.println(b1);
        System.out.println(bookService.find(0L, true, true));
        System.out.println(b2);
        System.out.println(bookService.find(1L, false, false));
        assertTrue("Ошибка в методе bookService.find(0L, true, true)", bookService.find(0L, true, true).equals(b1));
        assertTrue("Ошибка в методе bookService.find(0L, false, false)", bookService.find(1L, false, false).equals(b2));
    }


    @Test
    public void find1() {
        System.out.println(books);
        System.out.println(bookService.find(null, 3, 1, SortType.NAME));
        assertTrue("Ошибка в методе bookService.find(null, 3, 1, SortType.NAME)", bookService.find(null, 3, 1, SortType.NAME).equals(books));
    }


    @Test
    public void save() throws Exception{
        Book book = new Book();
        book.setName("1234");
        MultipartFile multipartFile1 = new MockMultipartFile("file1", new byte[0]);
        MultipartFile multipartFile2 = new MockMultipartFile("file2", new byte[0]);

        Answer answer = new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Book book = (Book)invocation.getArguments()[0];
                assertTrue("неидентичны контенты", book.getBookContents().iterator().next().getContent().equals(new javax.sql.rowset.serial.SerialBlob(multipartFile1.getBytes())));
                assertTrue("неидентичны картинки", Arrays.equals(book.getImage(), multipartFile2.getBytes()));
                return 1L;
            }
        };
        //к моменту вызова bookDAO.save в bookService.save book будет заполнен, поэтому проверяем заполненность в методе answer (выше)
        doAnswer(answer).when(bookDAO).save(any(Book.class));

        bookService.save(book, multipartFile1, multipartFile2);

        //Проверяем количество вызовов bookDAO.save (можно проверить только у моков)
        verify(bookDAO, times(1)).save(any(Book.class));
    }
}