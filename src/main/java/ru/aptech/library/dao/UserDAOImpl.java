package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.User;

@Repository
public class UserDAOImpl {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public User findByUserName(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("from User where username=:username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    @Transactional
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

}