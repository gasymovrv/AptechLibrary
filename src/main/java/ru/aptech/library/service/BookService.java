package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.dao.UserDAO;
import ru.aptech.library.dao.impl.AuthorDAOImpl;
import ru.aptech.library.dao.impl.BookDAOImpl;
import ru.aptech.library.dao.impl.UserDAOImpl;
import ru.aptech.library.dao.impl.UsersViewsDAOImpl;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.User;
import ru.aptech.library.entities.UsersViews;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaBooks;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class BookService {
    @Autowired
    @Qualifier("bookDAO")
    private CommonDAO<Book> bookDAO;
    @Autowired
    @Qualifier("authorDAO")
    private CommonDAO<Author> authorDAO;
    @Autowired
    @Qualifier("usersViewsDAO")
    private UserDAO<UsersViews, Long> usersViewsDAO;
    @Autowired
    @Qualifier("userDAO")
    private UserDAO<User, String> userDAO;
    @Autowired
    protected UserService userService;

    @Transactional(propagation= Propagation.REQUIRED)
    public List<Book> find() {
        return bookDAO.find();
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Book find(long id) {
        return bookDAO.find(id);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public List<Book> find(SearchCriteriaBooks criteria, Integer booksOnPage, Integer selectedPage, SortType sortType) {
        int init = (selectedPage - 1) * booksOnPage;
        if (criteria != null && !criteria.isEmpty()) {
            return bookDAO.find(criteria, booksOnPage, init, sortType);
        }
        return bookDAO.find(booksOnPage, init, sortType);
    }

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(Book book, MultipartFile content, MultipartFile image) throws Exception {
        book.setContent(content.getBytes());
        book.setImage(image.getBytes());
        if(book.getViews()==null){book.setViews(0L);}
        book.setCreated(LocalDateTime.now());
        Long id = bookDAO.save(book);
        book.setAllField(bookDAO.find(id));
    }

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Book updatedBook, MultipartFile content, MultipartFile image, Long bookId) throws Exception {
        Book existBook = bookDAO.find(bookId);
        existBook.setAllField(updatedBook);
        if (content != null && content.getSize() > 0) {
            existBook.setContent(content.getBytes());
        }
        if (image != null && image.getSize() > 0) {
            existBook.setImage(image.getBytes());
        }
        bookDAO.update(existBook);
    }

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Long bookId) throws Exception {
        Book book = bookDAO.find(bookId);
        Author a = book.getAuthor();
        authorDAO.setViews(a.getId(),a.getViews()-book.getViews());
        List<User> users = userDAO.find();
        for (User u : users) {
            if (u.getCart().getBooks().contains(book)) {
                u.getCart().removeBook(book.getId());
                userService.update(u);
            }
        }
        usersViewsDAO.delete(book);
        bookDAO.delete(book);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Long getQuantityBooks(SearchCriteriaBooks criteria) {
        if (criteria != null && !criteria.isEmpty()) {
            return bookDAO.getQuantity(criteria);
        }
        return bookDAO.getQuantity();
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void increaseView(Long bookId){
        bookDAO.increaseView(bookId);
    }
}
