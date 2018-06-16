package ru.aptech.library.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaAuthors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Author find(Long id, Boolean initSets) {
        Author a = authorDAO.find(id);
        if(initSets!=null && initSets){
            Hibernate.initialize(a.getBooks());
        }
        return a;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Author find(String fio) {
        return authorDAO.find(fio);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public List<Author> find(SearchCriteriaAuthors criteria, Integer authorsOnPage, Integer selectedPage, SortType sortType, Boolean initSets) {
        List<Author> authors;
        int init = (selectedPage - 1) * authorsOnPage;
        if (criteria != null && !criteria.isEmpty()) {
            authors = authorDAO.find(criteria, authorsOnPage, init, sortType);
        } else {
            authors = authorDAO.find(authorsOnPage, init, sortType);
        }
        if (initSets != null && initSets) {
            for (Author a : authors) {
                Hibernate.initialize(a.getBooks());
            }
        }
        return authors;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long save(Author author, String date) throws Exception {
        author.setBirthday(!date.equals("") ? LocalDate.parse(date, DATE_FORMAT) : null);
        Set<Book> bookList = author.getBooks();
        updateViews(author);
        author.setCreated(LocalDateTime.now());

        //Карта: старый автор и количество просмотров которые надо у него вычесть после удаления книги
        Map<Author, Long> oldAuthors = new HashMap<>();
        for (Book b : bookList) {
            Author oldAuthor = b.getAuthor();
            //добавляем просмотры по ключу автор
            oldAuthors.put(oldAuthor, oldAuthors.get(oldAuthor) == null ? b.getViews() : oldAuthors.get(oldAuthor) + b.getViews());
            b.setAuthor(author);
            bookDAO.update(b);
        }
        for (Map.Entry<Author, Long> entry: oldAuthors.entrySet()) {
            removeViews(entry.getKey(), entry.getValue());
            authorDAO.update(entry.getKey());
        }
        return authorDAO.save(author);
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

        //Карта: старый автор и количество просмотров которые надо у него вычесть после удаления книги
        Map<Author, Long> oldAuthors = new HashMap<>();
        //новый список книг
        for (Book nB : newBookList) {
            Author oldAuthor = nB.getAuthor();
            if (!oldAuthor.equals(existAuthor)) {
                //добавляем просмотры по ключу автор
                oldAuthors.put(oldAuthor, oldAuthors.get(oldAuthor) == null ? nB.getViews() : oldAuthors.get(oldAuthor) + nB.getViews());
            }
            nB.setAuthor(existAuthor);
            bookDAO.update(nB);
        }
        for (Map.Entry<Author, Long> entry: oldAuthors.entrySet()) {
            removeViews(entry.getKey(), entry.getValue());
            authorDAO.update(entry.getKey());
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
