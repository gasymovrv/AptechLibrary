package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.Book;
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
    public List<Book> find() {
        Session session = sessionFactory.getCurrentSession();
        List<Book> bookList = session.createQuery(BOOKS_WITHOUT_CONTENT + ORDER_BY_NAME,
                Book.class).getResultList();
        return bookList;
    }

    @Transactional
    public List<Book> find(Integer booksOnPage, Integer selectedPage) {
        int init = (selectedPage - 1) * booksOnPage;
        Session session = sessionFactory.getCurrentSession();
        List<Book> bookList = session.createQuery(BOOKS_WITHOUT_CONTENT + ORDER_BY_NAME,
                Book.class).setFirstResult(init).setMaxResults(booksOnPage).getResultList();
        return bookList;
    }


    @Transactional
    public Book find(long id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.createQuery(BOOKS_WITHOUT_CONTENT
                        + " where b.id=:id",
                Book.class).setParameter("id", id).getSingleResult();
        return book;
    }

    @Transactional
    public Book findWithContent(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }


    @Transactional
    public List<Book> find(SearchCriteria criteria, Integer booksOnPage, Integer selectedPage) {
        int init = (selectedPage - 1) * booksOnPage;
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery(BOOKS_WITHOUT_CONTENT +
                        " where b.genre.id=:genre" +
                        " or b.publisher.id=:publisher" +
                        " or b.author.id=:author" +
                        " or b.name like CONCAT(:letter, '%')" +
                        " or " + getSqlBySearchType(criteria.getSearchType()) +
                        " like CONCAT('%', :text, '%')" +
                        ORDER_BY_NAME,
                Book.class)
                .setParameter("genre", criteria.getGenreId())
                .setParameter("publisher", criteria.getPublisherId())
                .setParameter("author", criteria.getAuthorId())
                .setParameter("letter", criteria.getLetter())
                .setParameter("text", criteria.getText())
                .setFirstResult(init).setMaxResults(booksOnPage).getResultList();
        return books;
    }


    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.update(book);
    }

    @Transactional
    public void delete(Long bookId) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(find(bookId));
    }

    @Transactional
    public byte[] getBookContent(long id) {
        Session session = sessionFactory.getCurrentSession();
        byte[] content = (byte[])session.createQuery("select b.content from Book b where b.id=:id")
                .setParameter("id", id).getSingleResult();
        return content;
    }


    @Transactional
    public Long getQuantityBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(*) from Book", Long.class).getSingleResult();
    }

    @Transactional
    public Long getQuantityBooks(SearchCriteria criteria) {
        Session session = sessionFactory.getCurrentSession();

        Long result = session.createQuery("select count(*) from Book b" +
                        " where b.genre.id=:genre" +
                        " or b.publisher.id=:publisher" +
                        " or b.author.id=:author" +
                        " or b.name like CONCAT(:letter, '%')" +
                        " or " + getSqlBySearchType(criteria.getSearchType()) +
                        " like CONCAT('%', :text, '%')",
                Long.class)
                .setParameter("genre", criteria.getGenreId())
                .setParameter("publisher", criteria.getPublisherId())
                .setParameter("author", criteria.getAuthorId())
                .setParameter("letter", criteria.getLetter())
                .setParameter("text", criteria.getText())
                .getSingleResult();
        return result;
    }

    private String getSqlBySearchType(SearchType searchType){
        String stringSearchType = null;
        switch (searchType) {
            case TITLE:
                stringSearchType = " b.name ";
                break;
            case AUTHOR:
                stringSearchType = " b.author.fio ";
                break;
            case GENRE:
                stringSearchType = " b.genre.name ";
                break;
            case PUBLISHER:
                stringSearchType = " b.publisher.name ";
                break;
        }
        return stringSearchType;
    }

//    @Transactional
//    public List<Book> find() {
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
