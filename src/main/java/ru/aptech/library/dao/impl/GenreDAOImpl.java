package ru.aptech.library.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.entities.Genre;

import java.util.List;

@Repository("genreDAO")
public class GenreDAOImpl implements CommonDAO<Genre> {
    private final String GENRES = "select g from Genre g";
    private final String ORDER_BY_NAME = " order by g.name";
    @Autowired
    private SessionFactory sessionFactory;

    public List<Genre> find() {
        Session session = sessionFactory.getCurrentSession();
        List<Genre> genreList = session.createQuery(GENRES + ORDER_BY_NAME,
                Genre.class).getResultList();
        return genreList;
    }

    public Genre find(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Genre> query = session.createQuery(GENRES + " where g.id=:id", Genre.class).setParameter("id", id);
        try {
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Long save(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.save(genre);
    }

    public void update(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(genre);
    }

    public void delete(Genre genre) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(genre);
    }


}
