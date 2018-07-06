package ru.aptech.library.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import ru.aptech.library.BaseTest;
import ru.aptech.library.TestUtil;
import ru.aptech.library.entities.Book;
import ru.aptech.library.service.*;
import ru.aptech.library.util.SearchCriteriaBooks;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class StandaloneBookControllerTest extends BaseTest {
    private MockMvc mockMvc;
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private List<Book> books = new ArrayList<>();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .build();
    }

    @Test
    public void searchByCriteria() throws Exception {
        Book b1 = new Book();
        b1.setId(1L);
        b1.setName("Книга1");
        b1.setViews(15L);
        b1.setDescr("Описание 1");

        Book b2= new Book();
        b2.setId(2L);
        b2.setName("Книга2");
        b2.setViews(8L);
        b2.setDescr("Описание 2");

        books.add(b1);
        books.add(b2);

        when(bookService.find(any(), any(), any(), any())).thenReturn(books);

        mockMvc.perform(post("/books/searchByCriteria")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(new SearchCriteriaBooks()))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Книга1")))
                .andExpect(jsonPath("$[0].views", is(15)))
                .andExpect(jsonPath("$[0].descr", is("Описание 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Книга2")))
                .andExpect(jsonPath("$[1].views", is(8)))
                .andExpect(jsonPath("$[1].descr", is("Описание 2")));

        //проверяем что был 1 вызов bookService.find
        verify(bookService, times(1)).find(any(), any(), any(), any());
        //и больше не было
        verifyNoMoreInteractions(bookService);
    }
}