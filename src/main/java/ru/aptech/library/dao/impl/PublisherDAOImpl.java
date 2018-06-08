package ru.aptech.library.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.entities.Publisher;

import java.util.List;

@Repository("publisherDAO")
public class PublisherDAOImpl implements CommonDAO<Publisher> {
    private final String PUBLISHERS = "select p from Publisher p";
    private final String ORDER_BY_NAME = " order by p.name";

    @Autowired
    private SessionFactory sessionFactory;

    public List<Publisher> find() {
        Session session = sessionFactory.getCurrentSession();
        List<Publisher> genreList = session.createQuery(PUBLISHERS + ORDER_BY_NAME,
                Publisher.class).getResultList();
        return genreList;
    }

    public Publisher find(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Publisher> query = session.createQuery(PUBLISHERS + " where p.id=:id", Publisher.class).setParameter("id", id);
        try {
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public Long save(Publisher publisher) {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.save(publisher);
    }

    public void update(Publisher publisher) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(publisher);
    }

    public void delete(Publisher publisher) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(publisher);
    }


}
