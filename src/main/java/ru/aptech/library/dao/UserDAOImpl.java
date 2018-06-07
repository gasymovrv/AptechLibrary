package ru.aptech.library.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.Cart;
import ru.aptech.library.entities.User;
import ru.aptech.library.entities.UsersViews;

import java.util.List;

@Repository("userDAO")
public class UserDAOImpl {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    protected BCryptPasswordEncoder bCrypt;

    @Transactional
    public User findByUserName(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("from User where username=:username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
    }

    public void updateCart(Cart cart) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(cart);
    }

    public void saveUsersViews(UsersViews usersViews) {
        Session session = sessionFactory.getCurrentSession();
        session.save(usersViews);
    }

    public UsersViews findUsersViews(Long usersViewsId){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UsersViews where id=:usersViewsId", UsersViews.class)
                .setParameter("usersViewsId",usersViewsId)
                .getSingleResult();
    }

    public UsersViews findUsersViews(User user, Book book){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UsersViews where user.username=:username and book.id=:bookId", UsersViews.class)
                .setParameter("username",user.getUsername())
                .setParameter("bookId", book.getId())
                .getSingleResult();
    }

    public List<UsersViews> findUsersViews(User user){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UsersViews where user.username=:username order by views desc", UsersViews.class)
                .setParameter("username",user.getUsername())
                .getResultList();
    }

    public List<UsersViews> findUsersViews(Book book){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UsersViews where book.id=:bookId", UsersViews.class)
                .setParameter("bookId",book.getId())
                .getResultList();
    }

    public void deleteUsersViews(Book book){
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