package ru.aptech.library.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.aptech.library.dao.UserDAO;
import ru.aptech.library.entities.User;

import java.util.List;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO<User, String> {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    protected BCryptPasswordEncoder bCrypt;

    public List<User> find() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User", User.class).getResultList();
    }


    public User find(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User where username=:username", User.class).setParameter("username", username);
        try {
           return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public String save(User user) {
        Session session = sessionFactory.getCurrentSession();
        return (String)session.save(user);
    }

    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
    }

    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

}