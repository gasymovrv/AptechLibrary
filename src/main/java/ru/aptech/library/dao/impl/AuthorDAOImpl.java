package ru.aptech.library.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.entities.Author;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaAuthors;

import java.util.List;

@Repository("authorDAO")
public class AuthorDAOImpl implements CommonDAO<Author> {
    private final String AUTHORS = "select a from Author a";
    private final String ORDER_BY_NAME = " order by a.fio";
    private final String ORDER_BY_CREATION = " order by a.created desc";
    private final String ORDER_BY_POPULARITY = " order by a.views desc";

    @Autowired
    private SessionFactory sessionFactory;

    public List<Author> find() {
        Session session = sessionFactory.getCurrentSession();
        List<Author> authorList = session.createQuery(AUTHORS + " where fio != 'Неизвестный автор'" + ORDER_BY_NAME,
                Author.class).getResultList();
        return authorList;
    }

    public Author find(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Author> query = session.createQuery(AUTHORS + " where a.id=:id", Author.class).setParameter("id", id);
        try {
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Author find(String fio) {
        Session session = sessionFactory.getCurrentSession();
        Author author = session.createQuery(AUTHORS + " where a.fio=:fio",
                Author.class).setParameter("fio", fio).getSingleResult();
        return author;
    }

    public List<Author> find(Integer authorsOnPage, Integer init, SortType sortType) {
        String sortSql = getSqlBySortType(sortType);
        Session session = sessionFactory.getCurrentSession();
        List<Author> authors = session.createQuery(AUTHORS +
                        " where fio != 'Неизвестный автор'" +
                        sortSql,
                Author.class)
                .setFirstResult(init).setMaxResults(authorsOnPage).getResultList();
        return authors;
    }

    public List<Author> find(SearchCriteriaAuthors criteria, Integer authorsOnPage, Integer init, SortType sortType) {
        String sortSql = getSqlBySortType(sortType);
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(AUTHORS +
                        " where a.fio like CONCAT('%', :text, '%')" +
                        " and fio != 'Неизвестный автор'" +
                        sortSql,
                Author.class)
                .setParameter("text", criteria.getText())
                .setFirstResult(init).setMaxResults(authorsOnPage).getResultList();

    }

    public Long save(Author author) {
        Session session = sessionFactory.getCurrentSession();

        return (Long)session.save(author);
    }

    public void update(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(author);
    }

    public void delete(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(author);
    }

    public Long getQuantity() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(*) from Author where fio != 'Неизвестный автор'", Long.class).getSingleResult();
    }

    public Long getQuantity(SearchCriteriaAuthors criteria) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(*) from Author a" +
                        " where a.fio like CONCAT('%', :text, '%')" +
                        " and fio != 'Неизвестный автор'",
                Long.class)
                .setParameter("text", criteria.getText())
                .getSingleResult();
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

    public void increaseView(Long authorId){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update Author set views = views + 1 where id=:id").setParameter("id", authorId).executeUpdate();
    }

    public void setViews(Long authorId, Long views){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update Author set views =:views where id=:id")
                .setParameter("id", authorId)
                .setParameter("views", views)
                .executeUpdate();
    }
}
