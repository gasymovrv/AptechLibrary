package ru.aptech.library.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.entities.*;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaBooks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("books/")
public class BookController extends BaseController{


    /**
     * Методы для работы с ajax
     * */
    @RequestMapping(value = "searchByCriteria", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<Book> searchByCriteria(@RequestParam(required = false) Integer selectedPage,
                                       @RequestParam(required = false) Integer booksOnPage,
                                       @RequestBody SearchCriteriaBooks criteria,
                                       HttpSession session) {
        if (selectedPage == null) {
            selectedPage = 1;
        }
        if (booksOnPage == null && session.getAttribute("booksOnPage") == null) {
            booksOnPage = PAGE_SIZE_VALUE;
            session.setAttribute("booksOnPage", booksOnPage);
        } else if (booksOnPage == null && session.getAttribute("booksOnPage") != null) {
            booksOnPage = (Integer)session.getAttribute("booksOnPage");
        } else {
            session.setAttribute("booksOnPage", booksOnPage);
        }

        List<Book> books;
        if(criteria.isEmpty() && session.getAttribute("criteriaBooks")==null){
            SearchCriteriaBooks scb = new SearchCriteriaBooks();
            session.setAttribute("criteriaBooks", scb);
            books = bookService.find(scb, booksOnPage, selectedPage, (SortType)session.getAttribute("sortType"));
        } else {
            session.setAttribute("criteriaBooks", criteria);
            books = bookService.find(criteria, booksOnPage, selectedPage, (SortType)session.getAttribute("sortType"));
        }

        return books;
    }
    @RequestMapping(value = "getQuantityBooks", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Long getQuantityBooks(@RequestBody SearchCriteriaBooks criteria) {
        return bookService.getQuantityBooks(criteria);
    }
    @RequestMapping(value = "getCriteria", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SearchCriteriaBooks getCriteria(HttpSession session) {
        return (SearchCriteriaBooks)session.getAttribute("criteriaBooks");
    }
    @RequestMapping(value = "getBooksOnPage", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object getBooksOnPage(HttpSession session) {
        Object o = session.getAttribute("booksOnPage");
        if (o == null) {
            return PAGE_SIZE_VALUE;
        }
        return o;
    }
    @RequestMapping(value = "isValidBookContent", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isValidBookContent(@RequestParam("bookId") Long bookId) {
        Book book = bookService.find(bookId, false, true);
        if (book.getContentType() != null &&
                (book.getContentType().equals("application/octet-stream")
                        || book.getContentType().equals("application/x-zip-compressed")
                        || book.getContentType().equals("application/epub+zip")
                        || book.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                )) {
            return false;
        }
        return book.getContentType() != null;
    }
    @RequestMapping(value = "isEmptyBookContent", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isEmptyBookContent(@RequestParam("bookId") Long bookId) {
        Book book = bookService.find(bookId, false, true);
        return book.getContentType() == null;
    }


    @RequestMapping(value = "bookInfo", method = RequestMethod.GET)
    public ModelAndView bookInfo(@RequestParam(value = "bookId") Long bookId,
                                 @RequestParam(value = "errorContent", required = false) Boolean errorContent,
                                 Principal principal) {
        ModelAndView modelAndView = new ModelAndView("home-page-one-book");
        addAttributesForCriteria(modelAndView);
        Book book = bookService.find(bookId, false, true);
        increaseBookViews(book, principal);
        modelAndView.addObject("book", book);
        modelAndView.addObject("errorContent", errorContent);
        return modelAndView;
    }

    @RequestMapping(value = "addBookView", method = RequestMethod.GET)
    public ModelAndView addBookView() throws SQLException {
        ModelAndView modelAndView = new ModelAndView("add-book-page");
        addAttributesForAddOrEditBook(modelAndView, new Book());
        return modelAndView;
    }

    @RequestMapping(value = "addBookAction", method = RequestMethod.POST)
    public ModelAndView addBookAction(@ModelAttribute Book book,
                                      @RequestParam("file1") MultipartFile content,
                                      @RequestParam("file2") MultipartFile image) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("add-book-page");
        boolean isAdded;
        try {
            bookService.save(book, content, image);
            isAdded = true;
        } catch (Exception e){
            isAdded = false;
            e.printStackTrace();
        }
        modelAndView.addObject("isAdded", isAdded);
        addAttributesForAddOrEditBook(modelAndView, book);
        return modelAndView;
    }

    @RequestMapping(value = "editBookView", method = RequestMethod.GET)
    public ModelAndView editBookView(@RequestParam Long bookId) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("edit-book-page");
        addAttributesForAddOrEditBook(modelAndView, bookService.find(bookId, false, false));
        return modelAndView;
    }

    @RequestMapping(value = "editBookAction", method = RequestMethod.POST)
    public ModelAndView editBookAction(@ModelAttribute Book book,
                                      @RequestParam("file1") MultipartFile content,
                                      @RequestParam("file2") MultipartFile image,
                                       @RequestParam Long bookId) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("edit-book-page");
        boolean isEdited;
        try {
            bookService.update(book, content, image, bookId);
            isEdited = true;
        } catch (Exception e){
            isEdited = false;
            e.printStackTrace();
        }
        modelAndView.addObject("isEdited", isEdited);
        addAttributesForAddOrEditBook(modelAndView, bookService.find(bookId, false, false));
        return modelAndView;
    }

    @RequestMapping(value = "deleteBook", method = RequestMethod.GET)
    public String deleteBook(@RequestParam(value = "bookId") Long bookId, HttpSession session) {
        boolean isDeleted;
        try {
            bookService.delete(bookId);
            isDeleted = true;
        } catch (Exception e){
            isDeleted = false;
            e.printStackTrace();
        }
        session.setAttribute("isDeleted", isDeleted);
        return "redirect:/home";
    }

    @RequestMapping(value = "showBookImage", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void showImage(@RequestParam("bookId") Long bookId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        byte[] bookImage = bookService.findImage(bookId);
        //получаем дефолтное изображение из статических ресурсов
        String path = request.getSession().getServletContext().getRealPath("/resources/img/nophoto.jpg");
        InputStream inputStream = new FileSystemResource(new File(path)).getInputStream();
        byte[] defaultImage = new byte[inputStream.available()];
        inputStream.read(defaultImage);

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        if(bookImage==null || bookImage.length==0){
            response.getOutputStream().write(defaultImage);
        } else {
            response.getOutputStream().write(bookImage);
        }
        response.getOutputStream().close();
    }


    @RequestMapping(value = "showBookContent", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void showBookContent(@RequestParam("bookId") Long bookId, HttpServletResponse response, Principal principal) throws IOException, SQLException {
        Book book = bookService.find(bookId, false, false);
        byte[] content = new byte[0];

        BookContent bookContent = null;
        if(book.getBookContents().iterator().hasNext()) {
            bookContent = book.getBookContents().iterator().next();
        }
        if (book.getContentType() != null && bookContent!=null) {
            Blob b = bookContent.getContent();
            content = b.getBytes(1,(int)b.length());
            response.setContentType(book.getContentType());
        }

        String resultFileName = utils.transliterate(book.getName());
        resultFileName = resultFileName.replaceAll(" ", "_");
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
        while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", resultFileName));
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
        response.getOutputStream().close();
        increaseBookViews(book, principal);
    }


    @RequestMapping(value = "downloadBookContent", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void downloadBookContent(@RequestParam("bookId") Long bookId, HttpServletResponse response, Principal principal) throws IOException, SQLException {
        Book book = bookService.find(bookId, false, false);
        byte[] content = new byte[0];

        BookContent bookContent = null;
        if(book.getBookContents().iterator().hasNext()) {
            bookContent = book.getBookContents().iterator().next();
        }
        if (book.getContentType() != null && bookContent!=null) {
            Blob b = bookContent.getContent();
            content = b.getBytes(1,(int)b.length());
            response.setContentType(book.getContentType());
        }

        String resultFileName = utils.transliterate(book.getName());
        resultFileName = resultFileName.replaceAll(" ", "_");
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", resultFileName + "." + book.getFileExtension()));
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
        response.getOutputStream().close();
        increaseBookViews(book, principal);
    }


    @RequestMapping(value = "addToCart", method = RequestMethod.GET)
    public String addToCart(@RequestParam("bookId") Long bookId, Principal principal) {
        Book book = bookService.find(bookId, false, true);
        User user = userService.find(principal.getName(), "booksInCart");
        user.getCart().getBooks().add(book);
        userService.update(user);
        return "redirect:/users/account?tab=cart";
    }


    private void addAttributesForAddOrEditBook(ModelAndView modelAndView, Book book) throws SQLException {
        List<Publisher> publishers = publisherService.find();
        List<Genre> genres = genreService.find();
        List<Author> authors = authorService.find();
        Author unknownAuthor = authorService.find(UNKNOWN_AUTHOR);
        modelAndView.addObject("genreList", genres);
        modelAndView.addObject("authorList", authors);
        modelAndView.addObject("unknownAuthor", unknownAuthor);
        modelAndView.addObject("publisherList", publishers);
        modelAndView.addObject("book", book);
        BookContent bookContent = null;
        if(book.getBookContents().iterator().hasNext()) {
            bookContent = book.getBookContents().iterator().next();
        }
        if (!StringUtils.isEmpty(book.getFileExtension()) && bookContent!=null && bookContent.getContent().length() > 0) {
            modelAndView.addObject("fileName", book.getName() + "." + book.getFileExtension());
        }
        if (book.getImage()!=null && book.getImage().length > 0) {
            String imageSize;
            if(book.getImage().length>1000000){
                imageSize = String.format(Locale.US, "%.2f %s", (double) book.getImage().length/1000000D, "Мб");
            } else {
                imageSize = String.format(Locale.US, "%.2f %s", (double) book.getImage().length/1000D, "Кб");
            }
            modelAndView.addObject("imageSize", imageSize);
        }
    }

    private void increaseBookViews(Book book, Principal principal) {
        bookService.increaseView(book.getId());
        authorService.increaseView(book.getAuthor().getId());
        userService.saveOrIncreaseUsersViews(principal, book);
    }


}
