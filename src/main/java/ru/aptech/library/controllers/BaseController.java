package ru.aptech.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.entities.Genre;
import ru.aptech.library.enums.SearchType;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.service.*;
import ru.aptech.library.util.SearchCriteriaBooks;
import ru.aptech.library.util.Utils;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/")
public class BaseController {
    protected static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Autowired
    protected BookService bookService;
    @Autowired
    protected GenreService genreService;
    @Autowired
    protected AuthorService authorService;
    @Autowired
    protected PublisherService publisherService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected BCryptPasswordEncoder bCrypt;
    @Autowired
    protected Utils utils;
    protected static final int PAGE_SIZE_VALUE = 6;
    protected static final String UNKNOWN_AUTHOR = "Неизвестный автор";

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView home(@RequestParam(required = false) Long authorId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("home-page-list-books");
        addAttributesForCriteria(modelAndView);
        if(session.getAttribute("booksOnPage") == null) {
            session.setAttribute("booksOnPage", PAGE_SIZE_VALUE);
        }
        SearchCriteriaBooks criteria = new SearchCriteriaBooks();
        criteria.setAuthorId(authorId);
        modelAndView.addObject("criteriaBooks", criteria);
        return modelAndView;
    }

    @RequestMapping(value = "aboutUs", method = RequestMethod.GET)
    public ModelAndView aboutUs() {
        ModelAndView modelAndView = new ModelAndView("about-us-page");
        return modelAndView;
    }



    /**
     * Методы для работы с ajax
     * */
    @RequestMapping(value = "getFoundResultText", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getFoundResultText(HttpSession session) {
        return (String)session.getAttribute("foundResultText");
    }
    @RequestMapping(value = "saveFoundResultText", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveFoundResultText(@RequestParam(required = false) String foundResultText, HttpSession session) {
        session.setAttribute("foundResultText", foundResultText);
    }
    @RequestMapping(value = "getSortType", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SortType getSortType(HttpSession session) {
        return (SortType)session.getAttribute("sortType");
    }
    @RequestMapping(value = "saveSortType", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveSortType(@RequestParam SortType sortType, HttpSession session) {
        session.setAttribute("sortType", sortType);
    }


    protected void addAttributesForCriteria(ModelAndView modelAndView) {
        List<Genre> genres = genreService.find();
        Character[] letters = utils.getLetters();
        SearchType[] searchTypeList = SearchType.values();
        modelAndView.addObject("genreList", genres);
        modelAndView.addObject("letters", letters);
        modelAndView.addObject("searchTypeList", searchTypeList);
        modelAndView.addObject("sortTypeList", SortType.values());
    }

}
