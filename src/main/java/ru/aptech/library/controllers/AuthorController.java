package ru.aptech.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.dao.AuthorDAOImpl;
import ru.aptech.library.entities.Author;
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
        if (selectedPage == null) {selectedPage = 1;}
        if(authorsOnPage == null) {authorsOnPage = PAGE_SIZE_VALUE;}
        List<Author> authors = authorDAO.find(authorsOnPage, selectedPage);
        session.setAttribute("authorsOnPage", authorsOnPage);
        modelAndView.addObject("authorList", authors);
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
