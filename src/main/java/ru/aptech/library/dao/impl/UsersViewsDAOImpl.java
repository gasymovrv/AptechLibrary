package ru.aptech.library.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.aptech.library.dao.UserDAO;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.User;
import ru.aptech.library.entities.UsersViews;

import java.util.List;


@Repository("usersViewsDAO")
public class UsersViewsDAOImpl implements UserDAO<UsersViews, Long> {
    @Autowired
    private SessionFactory sessionFactory;

    public List<UsersViews> find() {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("from UsersViews ", UsersViews.class)
                .getResultList();
    }


    public UsersViews find(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<UsersViews> query = session.createQuery("from UsersViews where id=:id", UsersViews.class).setParameter("id", id);
        try {
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public UsersViews find(User user, Book book){
        Session session = sessionFactory.getCurrentSession();
        Query<UsersViews> query = session.createQuery(
                "from UsersViews where user.username=:username and book.id=:bookId", UsersViews.class)
                .setParameter("username",user.getUsername())
                .setParameter("bookId", book.getId());
        try {
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<UsersViews> find(User user){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UsersViews where user.username=:username order by views desc", UsersViews.class)
                .setParameter("username",user.getUsername())
                .getResultList();
    }

    public Long save(UsersViews usersViews) {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.save(usersViews);
    }

    public void update(UsersViews usersViews){
        Session session = sessionFactory.getCurrentSession();
        session.merge(usersViews);
    }


    @Override
    public void delete(UsersViews usersViews) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(usersViews);
    }


    public void delete(Book book){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete UsersViews where book.id=:bookId")
                .setParameter("bookId", book.getId())
                .executeUpdate();
    }

    public void increaseView(UsersViews usersViews){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update UsersViews set views = views + 1 where " +
                "user.username=:username " +
                "and " +
                "book.id=:bookId")
                .setParameter("username", usersViews.getUser().getUsername())
                .setParameter("bookId", usersViews.getBook().getId())
                .executeUpdate();
    }

}