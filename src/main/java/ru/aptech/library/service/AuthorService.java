package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.dao.impl.AuthorDAOImpl;
import ru.aptech.library.dao.impl.BookDAOImpl;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaAuthors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class AuthorService {
    protected static final String UNKNOWN_AUTHOR = "Неизвестный автор";
    protected static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Autowired
    @Qualifier("bookDAO")
    private CommonDAO<Book> bookDAO;
    @Autowired
    @Qualifier("authorDAO")
    private CommonDAO<Author> authorDAO;

    @Transactional(propagation=Propagation.REQUIRED)
    public List<Author> find() {
        return authorDAO.find();
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Author find(Long id) {
        return authorDAO.find(id);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Author find(String fio) {
        return authorDAO.find(fio);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public List<Author> find(SearchCriteriaAuthors criteria, Integer authorsOnPage, Integer selectedPage, SortType sortType) {
        int init = (selectedPage - 1) * authorsOnPage;
        if (criteria != null && !criteria.isEmpty()) {
            return authorDAO.find(criteria, authorsOnPage, init, sortType);
        }
        return authorDAO.find(authorsOnPage, init, sortType);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long save(Author author, String date) throws Exception {
        author.setBirthday(!date.equals("") ? LocalDate.parse(date, DATE_FORMAT) : null);
        Set<Book> bookList = author.getBooks();
        updateViews(author);
        author.setCreated(LocalDateTime.now());
        Long id = authorDAO.save(author);
        for (Book b : bookList) {
            b.setAuthor(author);
            bookDAO.update(b);
        }
        return id;
    }

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Author author, Long authorId, String date) throws Exception {
        author.setBirthday(!date.equals("") ? LocalDate.parse(date, DATE_FORMAT) : null);
        Author existAuthor = authorDAO.find(authorId);
        Set<Book> newBookList = author.getBooks();
        Set<Book> oldBookList = existAuthor.getBooks();
        //стрый список книг
        for (Book oB : oldBookList) {
            if (!newBookList.contains(oB)) {
                oB.setAuthor(authorDAO.find(UNKNOWN_AUTHOR));
                bookDAO.update(oB);
            }
        }
        existAuthor.setAllField(author);
        updateViews(existAuthor);
        //новый список книг
        for (Book nB : newBookList) {
            Author oldAuthor = nB.getAuthor();
            if (!oldAuthor.equals(existAuthor)) {
                removeViews(oldAuthor, nB.getViews());//удаляем просмотры у старого автора
                authorDAO.update(oldAuthor);
            }
            nB.setAuthor(existAuthor);
            bookDAO.update(nB);
        }
        authorDAO.update(existAuthor);
    }

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Long authorId) throws Exception{
        Author author = authorDAO.find(authorId);
        for (Book b : author.getBooks()) {
            b.setAuthor(authorDAO.find(UNKNOWN_AUTHOR));
            bookDAO.update(b);
        }
        authorDAO.delete(author);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Long getQuantityAuthors(SearchCriteriaAuthors criteria) {
        if (criteria != null && !criteria.isEmpty()) {
            return authorDAO.getQuantity(criteria);
        }
        return authorDAO.getQuantity();
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void increaseView(Long authorId){
        authorDAO.increaseView(authorId);
    }

    private void updateViews(Author author){
        author.setViews(0L);
        for (Book b : author.getBooks()) {
            author.setViews(author.getViews() + b.getViews());
        }
    }

    private void removeViews(Author author, Long views){
        author.setViews(author.getViews()-views);
    }



}
