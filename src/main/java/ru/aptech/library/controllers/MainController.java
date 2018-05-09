package ru.aptech.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.dao.BookDAOImpl;
import ru.aptech.library.dao.GenreDAOImpl;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.Genre;
import ru.aptech.library.enums.SearchType;
import ru.aptech.library.util.SearchCriteria;
import ru.aptech.library.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private BookDAOImpl bookDAO;
    @Autowired
    private GenreDAOImpl genreDAO;
    @Autowired
    private Utils utils;
    private static final int PAGE_SIZE_VALUE = 6;

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView home(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("home-page-list-books");
        addAttributesForCriteria(modelAndView);
        if(session.getAttribute("booksOnPage") == null) {
            session.setAttribute("booksOnPage", PAGE_SIZE_VALUE);
        }
        session.setAttribute("criteria", new SearchCriteria());
        session.setAttribute("foundResultText", "");
        return modelAndView;
    }


    @RequestMapping(value = "searchByCriteria", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody List<Book> searchByCriteria(@RequestParam(required = false) Integer selectedPage,
                                                 @RequestParam(required = false) Integer booksOnPage,
                                                 @RequestBody SearchCriteria criteria,
                                                     HttpSession session) {
        if (selectedPage == null) {selectedPage = 1;}
        if (booksOnPage == null) {booksOnPage = PAGE_SIZE_VALUE;}
        session.setAttribute("booksOnPage", booksOnPage);
        if(criteria.isEmpty()){
            session.setAttribute("criteria", new SearchCriteria());
            return bookDAO.getBooks(booksOnPage, selectedPage);
        }
        session.setAttribute("criteria", criteria);
        return bookDAO.getBooks(criteria, booksOnPage, selectedPage);
    }

    @RequestMapping(value = "getQuantityBooks", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Long getQuantityBooks(@RequestBody SearchCriteria criteria) {
        if(criteria.isEmpty()){
            return bookDAO.getQuantityBooks();
        }
        return bookDAO.getQuantityBooks(criteria);
    }


    @RequestMapping(value = "saveFoundResultText", method = RequestMethod.POST, consumes = "application/json")
    public void saveFoundResultText(@RequestParam String foundResultText, HttpSession session) {
        session.setAttribute("foundResultText", foundResultText);
    }

    @RequestMapping(value = "getCriteria", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody SearchCriteria getCriteria(HttpSession session) {
        return (SearchCriteria)session.getAttribute("criteria");
    }
    @RequestMapping(value = "getBooksOnPage", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getBooksOnPage(HttpSession session) {
        return session.getAttribute("booksOnPage");
    }
    @RequestMapping(value = "getFoundResultText", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public @ResponseBody String getFoundResultText(HttpSession session) {
        return (String)session.getAttribute("foundResultText");
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ModelAndView info(@RequestParam(value = "bookId", required = false) Long bookId) {
        ModelAndView modelAndView = new ModelAndView("home-page-one-book");
        addAttributesForCriteria(modelAndView);
        modelAndView.addObject("book", bookDAO.getBooks(bookId));
        modelAndView.addObject("criteria", new SearchCriteria());
        return modelAndView;
    }


    @RequestMapping(value = "showBookImage", method = RequestMethod.GET)
    public void showImage(@RequestParam("bookId") Long bookId, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        Book book = bookDAO.getBooks(bookId);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(book.getImage());
        response.getOutputStream().close();
    }


    @RequestMapping(value = "showBookContent", method = RequestMethod.GET)
    public void showPdf(@RequestParam("bookId") Long bookId, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        byte[] content = bookDAO.getBookContent(bookId);
        response.setContentType("application/pdf");
        response.getOutputStream().write(content);
        response.getOutputStream().close();
    }


    private void addAttributesForCriteria(ModelAndView modelAndView) {
        List<Genre> genres = genreDAO.getGenres();
        Character[] letters = utils.getLetters();
        SearchType[] searchTypeList = SearchType.values();

        modelAndView.addObject("genreList", genres);
        modelAndView.addObject("letters", letters);
        modelAndView.addObject("searchTypeList", searchTypeList);
    }

//Для тестового аджакс запроса - функция testGetBooks()
//    @RequestMapping(value = "home/searchResult", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody List<Book> searchResult() {
//        List<Book> books = bookDAO.getBooks();
//        return books;
//    }


//    @RequestMapping(value = "books", method = RequestMethod.GET)
//    public String books(@RequestParam(value = "bookId", required = false) String[] bookId, Model model) {
//        List<Book> bookList = bookDAO.getBooks();
//        List<Book> bookListFromParam = new ArrayList<>();
//        if (bookId != null && bookId.length != 0) {
//            for (String s : bookId) {
//                bookListFromParam.add(bookDAO.getBooks(Long.parseLong(s)));
//            }
//            model.addAttribute("bookListFromParam", bookListFromParam);
//        }
//        model.addAttribute("bookList", bookList);
//        return "test/books";
//    }
//
//
//    @RequestMapping(value = "test", method = RequestMethod.GET)
//    public String test() {
//        Book book = bookDAO.getBooks(20L);
//        List<Book> list1 = bookDAO.getBooks(new Author(0L, "эрих", null));
//        List<Book> list2 = bookDAO.getBooks("стив");
//        List<Book> list3 = bookDAO.getBooks(new Genre(0L, "прИкл", null));
//        List<Book> list4 = bookDAO.getBooks(new Character('б'));
//        return "test/main";
//    }
//
//
//    @RequestMapping(value = "lgn", method = RequestMethod.GET)
//    public String lgn() {
//        //это не вернет поток, а будет искать flows/auth.jsp! чтобы попасть в поток нужно писать redirect:flows/auth
//        return "flows/auth";
//    }


//    SpringSecurity
// @RequestMapping(value = { "/", "login" }, method = RequestMethod.GET)
//    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
//        ModelAndView model = new ModelAndView();
//        if (error != null) {
//            model.addObject("error", "Неверное имя пользователя или пароль!");
//        }
//        model.setViewName("login");
//        return model;
//    }
}
