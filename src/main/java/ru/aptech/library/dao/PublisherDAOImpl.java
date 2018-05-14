package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.Publisher;

import java.util.List;

@Repository
public class PublisherDAOImpl {
    private final String PUBLISHERS = "select p from Publisher p";
    private final String ORDER_BY_NAME = " order by p.name";

    @Autowired
    private SessionFactory sessionFactory;


    @Transactional
    public List<Publisher> getPublishers() {
        Session session = sessionFactory.getCurrentSession();
        List<Publisher> genreList = session.createQuery(PUBLISHERS + ORDER_BY_NAME,
                Publisher.class).getResultList();
        return genreList;
    }

    @Transactional
    public Publisher getPublishers(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Publisher Publisher = session.createQuery(PUBLISHERS + " where p.id=:id",
                Publisher.class).setParameter("id", id).getSingleResult();
        return Publisher;
    }


}
