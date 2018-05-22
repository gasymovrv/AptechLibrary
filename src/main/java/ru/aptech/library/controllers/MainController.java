package ru.aptech.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.dao.AuthorDAOImpl;
import ru.aptech.library.dao.BookDAOImpl;
import ru.aptech.library.dao.GenreDAOImpl;
import ru.aptech.library.dao.PublisherDAOImpl;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.Genre;
import ru.aptech.library.entities.Publisher;
import ru.aptech.library.enums.SearchType;
import ru.aptech.library.util.SearchCriteria;
import ru.aptech.library.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private BookDAOImpl bookDAO;
    @Autowired
    private GenreDAOImpl genreDAO;
    @Autowired
    private AuthorDAOImpl authorDAO;
    @Autowired
    private PublisherDAOImpl publisherDAO;
    @Autowired
    private Utils utils;
    private static final int PAGE_SIZE_VALUE = 6;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
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

    @RequestMapping(value = "/searchByCriteria", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
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

    @RequestMapping(value = "/bookInfo", method = RequestMethod.GET)
    public ModelAndView bookInfo(@RequestParam(value = "bookId") Long bookId) {
        ModelAndView modelAndView = new ModelAndView("home-page-one-book");
        addAttributesForCriteria(modelAndView);
        modelAndView.addObject("book", bookDAO.getBooks(bookId));
        return modelAndView;
    }


    /**
     * Методы для работы с ajax
     * */
    @RequestMapping(value = "/getQuantityBooks", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Long getQuantityBooks(@RequestBody SearchCriteria criteria) {
        if(criteria.isEmpty()){
            return bookDAO.getQuantityBooks();
        }
        return bookDAO.getQuantityBooks(criteria);
    }
    @RequestMapping(value = "/getCriteria", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody SearchCriteria getCriteria(HttpSession session) {
        return (SearchCriteria)session.getAttribute("criteria");
    }
    @RequestMapping(value = "/getBooksOnPage", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getBooksOnPage(HttpSession session) {
        Object o = session.getAttribute("booksOnPage");
        if (o == null) {
            return PAGE_SIZE_VALUE;
        }
        return o;
    }
    @RequestMapping(value = "/getFoundResultText", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public @ResponseBody String getFoundResultText(HttpSession session) {
        return (String)session.getAttribute("foundResultText");
    }
    @RequestMapping(value = "/saveFoundResultText", method = RequestMethod.POST, consumes = "application/json")
    public void saveFoundResultText(@RequestParam String foundResultText, HttpSession session) {
        session.setAttribute("foundResultText", foundResultText);
    }

    @RequestMapping(value = "/addBookView", method = RequestMethod.GET)
    public ModelAndView addBookView() {
        ModelAndView modelAndView = new ModelAndView("add-book-page");
        modelAndView.addObject("book", new Book());
        addAttributesForAddOrEditBook(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/addBookAction", method = RequestMethod.POST)
    public ModelAndView addBookAction(@ModelAttribute Book book,
                                      @RequestParam("file1") MultipartFile content,
                                      @RequestParam("file2") MultipartFile image) {
        ModelAndView modelAndView = new ModelAndView("add-book-page");
        boolean isAdded = saveBook(book, content, image);
        modelAndView.addObject("isAdded", isAdded);
        addAttributesForAddOrEditBook(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/editBookView", method = RequestMethod.GET)
    public ModelAndView editBookView(@RequestParam Long bookId) {
        ModelAndView modelAndView = new ModelAndView("edit-book-page");
        Book book = bookDAO.getBooks(bookId);
        modelAndView.addObject("book", book);
        addAttributesForAddOrEditBook(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/editBookAction", method = RequestMethod.POST)
    public ModelAndView editBookAction(@ModelAttribute Book book,
                                      @RequestParam("file1") MultipartFile content,
                                      @RequestParam("file2") MultipartFile image,
                                       @RequestParam Long bookId) {
        ModelAndView modelAndView = new ModelAndView("edit-book-page");
        boolean isEdited = updateBook(book, content, image, bookId);
        modelAndView.addObject("isEdited", isEdited);
        modelAndView.addObject("book", bookDAO.getBooks(bookId));
        addAttributesForAddOrEditBook(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/deleteBook", method = RequestMethod.GET)
    public ModelAndView deleteBook(@RequestParam(value = "bookId") Long bookId) {
        boolean isDeleted;
        try {
            bookDAO.deleteBook(bookId);
            isDeleted = true;
        } catch (Exception e){
            isDeleted = false;
        }
        ModelAndView modelAndView = new ModelAndView("home-page-list-books");
        addAttributesForCriteria(modelAndView);
        modelAndView.addObject("isDeleted", isDeleted);
        return modelAndView;
    }

    @RequestMapping(value = "/showBookImage", method = RequestMethod.GET)
    public void showImage(@RequestParam("bookId") Long bookId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Book book = bookDAO.getBooks(bookId);
        //получаем дефолтное изображение из статических ресурсов
        String path = request.getSession().getServletContext().getRealPath("/resources/img/nophoto.jpg");
        InputStream inputStream = new FileSystemResource(new File(path)).getInputStream();
        byte[] defaultImage = new byte[inputStream.available()];
        inputStream.read(defaultImage);

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        if(book.getImage()==null || book.getImage().length==0){
            response.getOutputStream().write(defaultImage);
        } else {
            response.getOutputStream().write(book.getImage());
        }
        response.getOutputStream().close();
    }


    @RequestMapping(value = "/showBookContent", method = RequestMethod.GET)
    public void showPdf(@RequestParam("bookId") Long bookId, HttpServletResponse response) throws IOException {
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


    private void addAttributesForAddOrEditBook(ModelAndView modelAndView) {
        List<Publisher> publishers = publisherDAO.getPublishers();
        List<Genre> genres = genreDAO.getGenres();
        List<Author> authors = authorDAO.getAuthors();
        modelAndView.addObject("genreList", genres);
        modelAndView.addObject("authorList", authors);
        modelAndView.addObject("publisherList", publishers);
    }


    private boolean saveBook(Book book, MultipartFile content, MultipartFile image) {
        try {
            book.setContent(content.getBytes());
            book.setImage(image.getBytes());
            bookDAO.saveBook(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private boolean updateBook(Book updatedBook, MultipartFile content, MultipartFile image, Long bookId) {
        try {
            Book existBook = bookDAO.getBooksWithContent(bookId);
            existBook.setAllField(updatedBook);
            if (content.getSize() > 0) {
                existBook.setContent(content.getBytes());
            }
            if (image.getSize() > 0 ) {
                existBook.setImage(image.getBytes());
            }
            bookDAO.updateBook(existBook);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//
//    @RequestMapping(value = "login", method = RequestMethod.GET)
//    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
//        ModelAndView model = new ModelAndView("login-page");
//        if (error != null) {
//            model.addObject("error", "Неверное имя пользователя или пароль!");
//        }
//        return model;
//    }
}
