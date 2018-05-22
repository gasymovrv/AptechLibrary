package ru.aptech.library.dao;

import java.util.ArrayList;
import java.util.List;
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
        return sessionFactory.getCurrentSession()
                .createQuery("from User where username=:username", User.class)
                .setParameter("username", username).getSingleResult();
    }

}