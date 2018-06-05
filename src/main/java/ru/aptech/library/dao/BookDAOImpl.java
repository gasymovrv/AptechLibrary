package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Book;
import ru.aptech.library.enums.SearchType;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaBooks;

import java.util.List;
import java.util.Set;

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
                    "b.voteCount," +
                    "b.views" +
                    ") from Book b";

    private final String ORDER_BY_NAME = " order by b.name";
    private final String ORDER_BY_CREATION = " order by b.created desc";
    private final String ORDER_BY_POPULARITY = " order by b.views desc";
    @Autowired
    private SessionFactory sessionFactory;


    public List<Book> find() {
        Session session = sessionFactory.getCurrentSession();
        List<Book> bookList = session.createQuery(BOOKS_WITHOUT_CONTENT + ORDER_BY_NAME,
                Book.class).getResultList();
        return bookList;
    }

    public Book find(long id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.createQuery(BOOKS_WITHOUT_CONTENT
                        + " where b.id=:id",
                Book.class).setParameter("id", id).getSingleResult();
        return book;
    }

    public Book findWithContent(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    public List<Book> find(Integer booksOnPage, Integer init, SortType sortType) {
        String sortSql = getSqlBySortType(sortType);
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery(BOOKS_WITHOUT_CONTENT + sortSql,
                    Book.class)
                    .setFirstResult(init).setMaxResults(booksOnPage).getResultList();
        return books;
    }

    public List<Book> find(SearchCriteriaBooks criteria, Integer booksOnPage, Integer init, SortType sortType) {
        String sortSql = getSqlBySortType(sortType);
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery(BOOKS_WITHOUT_CONTENT +
                            " where b.genre.id=:genre" +
                            " or b.publisher.id=:publisher" +
                            " or b.author.id=:author" +
                            " or b.name like CONCAT(:letter, '%')" +
                            " or " + getSqlBySearchType(criteria.getSearchType()) +
                            " like CONCAT('%', :text, '%')" +
                            sortSql,
                    Book.class)
                    .setParameter("genre", criteria.getGenreId())
                    .setParameter("publisher", criteria.getPublisherId())
                    .setParameter("author", criteria.getAuthorId())
                    .setParameter("letter", criteria.getLetter())
                    .setParameter("text", criteria.getText())
                    .setFirstResult(init).setMaxResults(booksOnPage).getResultList();
        return books;
    }

    public Long save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.save(book);
    }

    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(book);
    }

    public void delete(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.delete(book);
    }

    public byte[] getBookContent(long id) {
        Session session = sessionFactory.getCurrentSession();
        byte[] content = (byte[])session.createQuery("select b.content from Book b where b.id=:id")
                .setParameter("id", id).getSingleResult();
        return content;
    }

    public Long getQuantityBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(*) from Book", Long.class).getSingleResult();
    }

    public Long getQuantityBooks(SearchCriteriaBooks criteria) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(*) from Book b" +
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
    }

    private String getSqlBySearchType(SearchType searchType){
        String stringSearchType =  " b.name ";
        if (searchType!=null) {
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
        }
        return stringSearchType;
    }

    private String getSqlBySortType(SortType sortType){
        String sortSql = ORDER_BY_NAME;
        if(sortType!=null){
            switch (sortType){
                case NAME:
                    sortSql = ORDER_BY_NAME;
                    break;
                case CREATION_DATE:
                    sortSql = ORDER_BY_CREATION;
                    break;
                case POPULARITY:
                    sortSql = ORDER_BY_POPULARITY;
                    break;
            }
        }
        return sortSql;
    }

    public void increaseView(Long bookId){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update Book set views = views + 1 where id=:id").setParameter("id", bookId).executeUpdate();
    }

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
