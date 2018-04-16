package ru.aptech.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.dao.BookDAOImpl;
import ru.aptech.library.dao.GenreDAOImpl;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.Genre;
import ru.aptech.library.enums.SearchType;
import ru.aptech.library.util.Utils;

import java.util.ArrayList;
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


    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home-page-list-books");
        addGenreList(modelAndView);
        addLetters(modelAndView);
        addSearchTypes(modelAndView);
        return modelAndView;
    }


    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ModelAndView info(@RequestParam(value = "bookId", required = false) String bookId) {
        ModelAndView modelAndView = new ModelAndView("home-page-one-book");
        addGenreList(modelAndView);
        addLetters(modelAndView);
        addSearchTypes(modelAndView);
        return modelAndView;
    }


    private void addGenreList(ModelAndView modelAndView) {
        List<Genre> genres = genreDAO.getGenres();
        modelAndView.addObject("genreList", genres);
    }

    private void addLetters(ModelAndView modelAndView) {
        Character[] letters = utils.getLetters();
        modelAndView.addObject("letters", letters);
    }

    private void addSearchTypes(ModelAndView modelAndView) {
        List<SearchType> searchTypeList = utils.getSearchTypeList();
        SearchType selectedSearchType = utils.getSelectedSearchType();
        modelAndView.addObject("searchTypeList", searchTypeList);
        modelAndView.addObject("selectedSearchType", selectedSearchType);
    }






    @RequestMapping(value = "books", method = RequestMethod.GET)
    public String books(@RequestParam(value = "bookId", required = false) String[] bookId, Model model) {
        List<Book> bookList = bookDAO.getBooks();
        List<Book> bookListFromParam = new ArrayList<>();
        if (bookId != null && bookId.length != 0) {
            for (String s : bookId) {
                bookListFromParam.add(bookDAO.getBooks(Long.parseLong(s)));
            }
            model.addAttribute("bookListFromParam", bookListFromParam);
        }
        model.addAttribute("bookList", bookList);
        return "test/books";
    }


    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        Book book = bookDAO.getBooks(20L);
        List<Book> list1 = bookDAO.getBooks(new Author(0L, "эрих", null));
        List<Book> list2 = bookDAO.getBooks("стив");
        List<Book> list3 = bookDAO.getBooks(new Genre(0L, "прИкл", null));
        List<Book> list4 = bookDAO.getBooks(new Character('б'));
        return "test/main";
    }


    @RequestMapping(value = "lgn", method = RequestMethod.GET)
    public String lgn() {
        //это не вернет поток, а будет искать flows/auth.jsp! чтобы попасть в поток нужно писать redirect:flows/auth
        return "flows/auth";
    }


//    @RequestMapping(value = { "/", "login" }, method = RequestMethod.GET)
//    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
//        ModelAndView model = new ModelAndView();
//        if (error != null) {
//            model.addObject("error", "Неверное имя пользователя или пароль!");
//        }
//        model.setViewName("login");
//        return model;
//    }
}
