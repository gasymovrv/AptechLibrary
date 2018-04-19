package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.Genre;
import ru.aptech.library.enums.SearchType;
import ru.aptech.library.util.SearchCriteria;

import java.util.List;

@Repository
public class BookDAOImpl {
    //с помощью JPQL исключаем поле контент
    private final String BOOKS_WITHOUT_CONTENT =
            "select new Book(" +
                    "b.id, " +
                    "b.name, " +
                    "b.pageCount, " +
                    "b.isbn, " +
                    "b.genre, " +
                    "b.author, " +
                    "b.publishYear, " +
                    "b.publisher, " +
                    "b.image, " +
                    "b.descr, " +
                    "b.bookcol, " +
                    "b.rating, " +
                    "b.voteCount) from Book b";

    private final String ORDER_BY_NAME = " order by b.name";
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private GenreDAOImpl genreDAO;


    @Transactional
    public List<Book> getBooks() {
        Session session = sessionFactory.getCurrentSession();
        List<Book> bookList = session.createQuery(BOOKS_WITHOUT_CONTENT + ORDER_BY_NAME,
                Book.class).getResultList();
        return bookList;
    }


    @Transactional
    public Book getBooks(long id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.createQuery(BOOKS_WITHOUT_CONTENT
                        + " where b.id=:id",
                Book.class).setParameter("id", id).getSingleResult();
        return book;
    }


    @Transactional
    public List<Book> getBooks(Author author) {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery(BOOKS_WITHOUT_CONTENT
                        + " where b.author.fio like CONCAT('%', :author, '%')"
                        + ORDER_BY_NAME,
                Book.class).setParameter("author", author.getFio()).getResultList();
        return books;
    }


    @Transactional
    public List<Book> getBooks(String bookName) {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery(BOOKS_WITHOUT_CONTENT
                        + " where b.name like CONCAT('%', :name, '%')"
                        + ORDER_BY_NAME,
                Book.class).setParameter("name", bookName.toLowerCase()).getResultList();
        return books;
    }


    @Transactional
    public List<Book> getBooks(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery(BOOKS_WITHOUT_CONTENT
                        + " where b.genre.name like CONCAT('%', :genre, '%')"
                        + ORDER_BY_NAME,
                Book.class).setParameter("genre", genre.getName()).getResultList();
        return books;
    }


    @Transactional
    public List<Book> getBooks(Character letter) {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery(BOOKS_WITHOUT_CONTENT
                        + " where b.name like CONCAT(:letter, '%')"
                        + ORDER_BY_NAME,
                Book.class).setParameter("letter", letter).getResultList();
        return books;
    }


    @Transactional
    public List<Book> getBooksByCriteria(SearchCriteria criteria) {
        Session session = sessionFactory.getCurrentSession();
        Long genreId = criteria.getGenreId();
        Genre genre = genreId != null ? genreDAO.getGenres(genreId) : null;
        Character letter = criteria.getLetter();
        String text = criteria.getText();
        SearchType searchType = criteria.getSearchType();

        String textVariants = null;
        switch (searchType) {
            case TITLE:
                textVariants = " b.name ";
                break;
            case AUTHOR:
                textVariants = " b.author.fio ";
                break;
            case GENRE:
                textVariants = " b.genre.name ";
                break;
            case PUBLISHER:
                textVariants = " b.publisher.name ";
                break;
        }
        List<Book> books = session.createQuery(BOOKS_WITHOUT_CONTENT
                        + " where b.genre.name like CONCAT('%', :genre, '%') or b.name like CONCAT(:letter, '%') or "
                        + textVariants
                        + " like CONCAT('%', :text, '%')"
                        + ORDER_BY_NAME,
                Book.class)
                .setParameter("genre", genre != null ? genre.getName() : null)
                .setParameter("letter", letter)
                .setParameter("text", text)
                .getResultList();
        return books;
    }
//    @Transactional
//    public List<Book> getBooks() {
//        //Сессия
//        Session session = sessionFactory.getCurrentSession();
//        //Бюйлдер
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        //Критерия
//        CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
//        //Объект запроса Root
//        Root<Book> root = criteria.from(Book.class);
//        //Указываем тип запроса
////        criteria.select(root).where(builder.equal(root.get("id"), author));
//        //Подготавливаем запрос создавая Query
//        Query<Book> query = session.createQuery(criteria);
//        //Выполяем запрос
//        return query.getSingleResult();
//    }


}
