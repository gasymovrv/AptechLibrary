package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.Genre;

import java.util.List;

@Repository
public class GenreDAOImpl {
    private final String GENRES = "select g from Genre g";
    private final String ORDER_BY_NAME = " order by g.name";

    @Autowired
    private SessionFactory sessionFactory;


    @Transactional
    public List<Genre> getGenres() {
        Session session = sessionFactory.getCurrentSession();
        List<Genre> genreList = session.createQuery(GENRES + ORDER_BY_NAME,
                Genre.class).getResultList();
        return genreList;
    }


}
