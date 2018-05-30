package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.Author;

import java.time.LocalDate;
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
        List<Author> authorList = session.createQuery(AUTHORS + " where fio != 'Неизвестный автор'" + ORDER_BY_NAME,
                Author.class).getResultList();
        return authorList;
    }

    @Transactional
    public List<Author> find(Integer authorsOnPage, Integer selectedPage) {
        int init = (selectedPage - 1) * authorsOnPage;
        Session session = sessionFactory.getCurrentSession();
        List<Author> authorList = session.createQuery(AUTHORS + " where fio != 'Неизвестный автор'" + ORDER_BY_NAME,
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
    public Author find(String fio) {
        Session session = sessionFactory.getCurrentSession();
        Author author = session.createQuery(AUTHORS + " where a.fio=:fio",
                Author.class).setParameter("fio", fio).getSingleResult();
        return author;
    }


    @Transactional
    public Long save(Author author) {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.save(author);
    }

    @Transactional
    public void update(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.update(author);
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
