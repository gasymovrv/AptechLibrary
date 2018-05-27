package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.User;
import ru.aptech.library.entities.UserRole;

import java.util.List;

@Repository
public class UserRoleDAOImpl {
    @Autowired
    private SessionFactory sessionFactory;


    @Transactional
    public List<UserRole> find() {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("from UserRole", UserRole.class)
                .getResultList();
    }

    @Transactional
    public UserRole find(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UserRole where userRoleId=:id",
                UserRole.class).setParameter("id", id).getSingleResult();
    }

    @Transactional
    public UserRole findRoleUser() {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("from UserRole where role='ROLE_USER'", UserRole.class)
                .getSingleResult();
    }

    @Transactional
    public UserRole findRoleAdmin() {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("from UserRole where role='ROLE_ADMIN'", UserRole.class)
                .getSingleResult();
    }

}