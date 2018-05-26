package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.Author;
import ru.aptech.library.entities.Genre;

import java.util.List;

@Repository
public class AuthorDAOImpl {
    private final String AUTHORS = "select a from Author a";
    private final String ORDER_BY_NAME = " order by a.fio";

    @Autowired
    private SessionFactory sessionFactory;


    @Transactional
    public List<Author> find() {
        Session session = sessionFactory.getCurrentSession();
        List<Author> authorList = session.createQuery(AUTHORS + ORDER_BY_NAME,
                Author.class).getResultList();
        return authorList;
    }

    @Transactional
    public List<Author> find(Integer authorsOnPage, Integer selectedPage) {
        int init = (selectedPage - 1) * authorsOnPage;
        Session session = sessionFactory.getCurrentSession();
        List<Author> authorList = session.createQuery(AUTHORS + ORDER_BY_NAME,
                Author.class).setFirstResult(init).setMaxResults(authorsOnPage).getResultList();
        return authorList;
    }

    @Transactional
    public Author find(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Author author = session.createQuery(AUTHORS + " where a.id=:id",
                Author.class).setParameter("id", id).getSingleResult();
        return author;
    }

    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(find(id));
    }

    @Transactional
    public Long getQuantityAuthors() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(*) from Author ", Long.class).getSingleResult();
    }
}
