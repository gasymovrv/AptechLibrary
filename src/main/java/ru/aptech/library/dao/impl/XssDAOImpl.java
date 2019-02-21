package ru.aptech.library.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aptech.library.dao.UserDAO;
import ru.aptech.library.dao.XssDAO;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.User;
import ru.aptech.library.entities.UsersViews;
import ru.aptech.library.entities.XssData;

import java.util.List;


@Repository("xssDAO")
public class XssDAOImpl implements XssDAO{
    @Autowired
    private SessionFactory sessionFactory;

    public void save(XssData xssData) {
        Session session = sessionFactory.getCurrentSession();
        session.save(xssData);
    }

}