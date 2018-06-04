package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aptech.library.dao.AuthorDAOImpl;
import ru.aptech.library.dao.BookDAOImpl;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaBooks;

import java.util.List;

@Service
public class BookService {
    @Autowired
    protected BookDAOImpl bookDAO;
    @Autowired
    protected AuthorDAOImpl authorDAO;


    public List<Book> find() {
        return bookDAO.find();
    }


    public Book find(Long id) {
        try {
            return bookDAO.find(id);
        } catch (Exception e) {
            return null;
        }
    }


    public List<Book> find(SearchCriteriaBooks criteria, Integer booksOnPage, Integer selectedPage, SortType sortType) {
        int init = (selectedPage - 1) * booksOnPage;
        if (criteria != null && !criteria.isEmpty()) {
            return bookDAO.find(criteria, booksOnPage, init, sortType);
        }
        return bookDAO.find(booksOnPage, init, sortType);
    }

    public Book findWithContent(long id) {
        try {
            return bookDAO.findWithContent(id);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] getBookContent(long id) {
        try {
            return bookDAO.getBookContent(id);
        } catch (Exception e){
            return new byte[0];
        }
    }

    public boolean save(Book book, MultipartFile content, MultipartFile image) {
        try {
            book.setContent(content.getBytes());
            book.setImage(image.getBytes());
            Long id = bookDAO.save(book);
            book.setAllField(bookDAO.find(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(Book updatedBook, MultipartFile content, MultipartFile image, Long bookId) {
        try {
            Book existBook = bookDAO.findWithContent(bookId);
            existBook.setAllField(updatedBook);
            if (content!=null && content.getSize() > 0) {
                existBook.setContent(content.getBytes());
            }
            if (image!=null && image.getSize() > 0 ) {
                existBook.setImage(image.getBytes());
            }
            bookDAO.update(existBook);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Long bookId) {
        try {
            Book existBook = bookDAO.find(bookId);
            Author author = existBook.getAuthor();
            author.getBooks().remove(existBook);
            authorDAO.update(author);
            existBook.setAuthor(null);
            bookDAO.delete(existBook);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Long getQuantityBooks(SearchCriteriaBooks criteria) {
        if (criteria != null && !criteria.isEmpty()) {
            return bookDAO.getQuantityBooks(criteria);
        }
        return bookDAO.getQuantityBooks();
    }
}
