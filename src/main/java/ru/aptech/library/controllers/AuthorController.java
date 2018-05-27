package ru.aptech.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.dao.AuthorDAOImpl;
import ru.aptech.library.entities.Author;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteria;
import ru.aptech.library.util.Utils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthorController {
    @Autowired
    private AuthorDAOImpl authorDAO;
    @Autowired
    private Utils utils;
    private static final int PAGE_SIZE_VALUE = 6;

    @RequestMapping(value = "/authors/list", method = RequestMethod.GET)
    public ModelAndView authorList(@RequestParam(required = false) Integer authorsOnPage,
                                   @RequestParam(required = false) Integer selectedPage,
                                   HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("author-page-list-author");
        if (selectedPage == null) {
            selectedPage = 1;
        }
        if (authorsOnPage == null && session.getAttribute("authorsOnPage") == null) {
            authorsOnPage = PAGE_SIZE_VALUE;
            session.setAttribute("authorsOnPage", authorsOnPage);
        } else if (authorsOnPage == null && session.getAttribute("authorsOnPage") != null) {
            authorsOnPage = (Integer)session.getAttribute("authorsOnPage");
        } else {
            session.setAttribute("authorsOnPage", authorsOnPage);
        }
        List<Author> authors = authorDAO.find(authorsOnPage, selectedPage);
        modelAndView.addObject("authorList", authors);
        modelAndView.addObject("sortType", SortType.values());
        modelAndView.addObject("selectedPage", selectedPage);
        modelAndView.addObject("quantityAuthors", authorDAO.getQuantityAuthors());
        return modelAndView;
    }

    @RequestMapping(value = "/authors/authorInfo", method = RequestMethod.GET)
    public ModelAndView authorInfo(@RequestParam(value = "authorId") Long authorId) {
        ModelAndView modelAndView = new ModelAndView("author-page-one-author");
        modelAndView.addObject("author", authorDAO.find(authorId));
        return modelAndView;
    }


    @RequestMapping(value = "/authors/getAuthorName", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public @ResponseBody String getAuthorName(@RequestParam Long authorId) {
        return authorDAO.find(authorId).getFio();
    }

//    @RequestMapping(value = "/addBookView", method = RequestMethod.GET)
//    public ModelAndView addBookView() {
//        ModelAndView modelAndView = new ModelAndView("add-book-page");
//        modelAndView.addObject("book", new Book());
//        addAttributesForAddOrEditBook(modelAndView);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/addBookAction", method = RequestMethod.POST)
//    public ModelAndView addBookAction(@ModelAttribute Book book,
//                                      @RequestParam("file1") MultipartFile content,
//                                      @RequestParam("file2") MultipartFile image) {
//        ModelAndView modelAndView = new ModelAndView("add-book-page");
//        boolean isAdded = saveBook(book, content, image);
//        modelAndView.addObject("isAdded", isAdded);
//        addAttributesForAddOrEditBook(modelAndView);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/editBookView", method = RequestMethod.GET)
//    public ModelAndView editBookView(@RequestParam Long bookId) {
//        ModelAndView modelAndView = new ModelAndView("edit-book-page");
//        Book book = bookDAO.getBooks(bookId);
//        modelAndView.addObject("book", book);
//        addAttributesForAddOrEditBook(modelAndView);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/editBookAction", method = RequestMethod.POST)
//    public ModelAndView editBookAction(@ModelAttribute Book book,
//                                      @RequestParam("file1") MultipartFile content,
//                                      @RequestParam("file2") MultipartFile image,
//                                       @RequestParam Long bookId) {
//        ModelAndView modelAndView = new ModelAndView("edit-book-page");
//        boolean isEdited = updateBook(book, content, image, bookId);
//        modelAndView.addObject("isEdited", isEdited);
//        modelAndView.addObject("book", bookDAO.getBooks(bookId));
//        addAttributesForAddOrEditBook(modelAndView);
//        return modelAndView;
//    }
//
    @RequestMapping(value = "/authors/deleteAuthor", method = RequestMethod.GET)
    public ModelAndView deleteAuthor(@RequestParam(value = "authorId") Long authorId) {
        boolean isDeleted;
        try {
            authorDAO.delete(authorId);
            isDeleted = true;
        } catch (Exception e){
            isDeleted = false;
        }
        ModelAndView modelAndView = new ModelAndView("author-page-list-author");
        modelAndView.addObject("isDeleted", isDeleted);
        return modelAndView;
    }


}
