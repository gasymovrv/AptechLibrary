package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aptech.library.dao.AuthorDAOImpl;
import ru.aptech.library.dao.BookDAOImpl;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaAuthors;
import ru.aptech.library.util.SearchCriteriaBooks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class AuthorService {
    protected static final String UNKNOWN_AUTHOR = "Неизвестный автор";
    protected static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Autowired
    protected BookDAOImpl bookDAO;
    @Autowired
    protected AuthorDAOImpl authorDAO;

    public List<Author> find() {
        try {
            return authorDAO.find();
        } catch (Exception e){
            return null;
        }
    }

    public Author find(Long id) {
        try {
            return authorDAO.find(id);
        } catch (Exception e){
            return null;
        }
    }

    public Author find(String fio) {
        try {
            return authorDAO.find(fio);
        } catch (Exception e){
            return null;
        }
    }

    public List<Author> find(SearchCriteriaAuthors criteria, Integer authorsOnPage, Integer selectedPage, SortType sortType) {
        int init = (selectedPage - 1) * authorsOnPage;
        if (criteria != null && !criteria.isEmpty()) {
            return authorDAO.find(criteria, authorsOnPage, init, sortType);
        }
        return authorDAO.find(authorsOnPage, init, sortType);
    }

    public boolean save(Author author, String date){
        try {
            author.setBirthday(!date.equals("") ? LocalDate.parse(date, DATE_FORMAT) : null);
            Set<Book> bookList = author.getBooks();
            Author savedAuthor = authorDAO.find(authorDAO.save(author));
            for(Book b : bookList) {
                b.setAuthor(savedAuthor);
                bookDAO.update(b);
            }
            //какая-то херня, но без нее почему-то селектор с книгами на странице add-or-edit-author.jsp очищается
            author.setAllField(authorDAO.find(savedAuthor.getId()));
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean update(Author author, Long authorId, String date){
        try {
            author.setBirthday(!date.equals("") ? LocalDate.parse(date, DATE_FORMAT) : null);
            Author existAuthor = authorDAO.find(authorId);
            Set<Book> newBookList = author.getBooks();
            Set<Book> oldBookList = existAuthor.getBooks();
            for(Book oB : oldBookList) {
                if(!newBookList.contains(oB)){
                    oB.setAuthor(authorDAO.find(UNKNOWN_AUTHOR));
                    bookDAO.update(oB);
                }
            }
            existAuthor.setAllField(author);
            authorDAO.update(existAuthor);
            for(Book nB : newBookList) {
                nB.setAuthor(existAuthor);
                bookDAO.update(nB);
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean delete(Long authorId){
        try {
            Author author = authorDAO.find(authorId);
            for(Book b : author.getBooks()){
                b.setAuthor(authorDAO.find(UNKNOWN_AUTHOR));
                bookDAO.update(b);
            }
            authorDAO.delete(author);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    public Long getQuantityAuthors(SearchCriteriaAuthors criteria) {
        if (criteria != null && !criteria.isEmpty()) {
            return authorDAO.getQuantityAuthors(criteria);
        }
        return authorDAO.getQuantityAuthors();
    }

}
