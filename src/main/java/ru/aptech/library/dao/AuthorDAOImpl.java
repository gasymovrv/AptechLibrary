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
    public List<Author> getAuthors() {
        Session session = sessionFactory.getCurrentSession();
        List<Author> genreList = session.createQuery(AUTHORS + ORDER_BY_NAME,
                Author.class).getResultList();
        return genreList;
    }

    @Transactional
    public Author getAuthors(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Author author = session.createQuery(AUTHORS + " where a.id=:id",
                Author.class).setParameter("id", id).getSingleResult();
        return author;
    }


}
