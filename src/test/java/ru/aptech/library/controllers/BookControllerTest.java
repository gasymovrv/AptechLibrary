package ru.aptech.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.aptech.library.BaseTest;
import ru.aptech.library.TestContext;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.Genre;
import ru.aptech.library.entities.Publisher;
import ru.aptech.library.service.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class) // это инициализирует контекст спринга
@WebAppConfiguration()
@ContextConfiguration(classes = {TestContext.class}) //путь к настройкам контекста, там же перечень бинов
public class BookControllerTest extends BaseTest {
    @Autowired
    WebApplicationContext wac;
    @Autowired
    MockHttpSession session;
    @Autowired
    MockHttpServletRequest request;

    private MockMvc mockMvc;

    //Это тоже моки, но они инициализируются в конфиге TestContext
    @Autowired
    private BookService bookService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private UserService userService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void addBookView() throws Exception {
        mockMvc.perform(get("/books/addBookView")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("add-book-page"))
                .andExpect(forwardedUrl("/WEB-INF/views/common/base.jsp"));
    }


    @Test
    public void addBookAction() throws Exception {
        Genre genre = new Genre();
        Publisher publisher = new Publisher();
        Author author = new Author();
        genre.setId(1L);
        publisher.setId(1L);
        author.setId(1L);

        Book b1 = new Book();
        b1.setId(1L);
        b1.setName("Книга1");
        b1.setDescr("Описание 1");
        b1.setPublishYear(1999);
        b1.setPageCount(100);
        b1.setAuthor(author);
        b1.setGenre(genre);
        b1.setPublisher(publisher);

        MockMultipartFile file1 = new MockMultipartFile("file1", "book.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file2", "image.txt", MediaType.TEXT_PLAIN_VALUE, "Image!".getBytes());
        mockMvc.perform(
                multipart("/books/addBookAction")
                        .file(file1)
                        .file(file2)
                       .flashAttr("book", b1)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("add-book-page"))
                .andExpect(forwardedUrl("/WEB-INF/views/common/base.jsp"))
                .andExpect(model().attribute("book", is(b1)));

        verify(bookService, times(1)).save(any(), any(), any());
    }
}