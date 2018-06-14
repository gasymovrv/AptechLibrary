package ru.aptech.library.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.dao.UserDAO;
import ru.aptech.library.dao.impl.UserDAOImpl;
import ru.aptech.library.dao.impl.UsersViewsDAOImpl;
import ru.aptech.library.entities.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    @Qualifier("userDAO")
    private UserDAO<User, String> userDAO;
    @Autowired
    @Qualifier("usersViewsDAO")
    private UserDAO<UsersViews, Long> usersViewsDAO;
    @Autowired
    protected BCryptPasswordEncoder bCrypt;

    @Transactional(propagation= Propagation.REQUIRED)
    public User find(String username, String... initSets) {
        User u = userDAO.find(username);
        if (initSets != null && initSets.length > 0) {
            for (String s : initSets) {
                switch (s) {
                    case "booksInCart": {
                        Hibernate.initialize(u.getCart().getBooks());
                        break;
                    }
                    case "booksInOrders": {
                        Hibernate.initialize(u.getOrders());
                        for (Order o : u.getOrders()) {
                            Hibernate.initialize(o.getBooks());
                        }
                        break;
                    }
                    case "orders": {
                        Hibernate.initialize(u.getOrders());
                        break;
                    }
                }
            }
        }
        return u;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(User user) {
        encodeUserPass(user);
        user.fillUserRoles();
        Cart c = new Cart();
        c.setUser(user);
        user.setCart(c);
        user.setMoney(0.0);
        userDAO.save(user);
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void update(User user) {
        userDAO.update(user);
    }


    @Transactional(propagation= Propagation.REQUIRED)
    public void buyBooksFromCart(User user) {
        Order o = new Order();
        o.setUser(user);
        o.setCreated(LocalDateTime.now());
        o.getBooks().addAll(user.getCart().getBooks());
        user.setMoney(user.getMoney()-user.getCart().getSum());
        user.getOrders().add(o);
        user.getCart().removeAllBooks();
        userDAO.update(user);
    }


    @Transactional(propagation= Propagation.REQUIRED)
    public void saveOrIncreaseUsersViews(Principal principal, Book book) {
        User user = null;
        UsersViews usersViews = null;
        try {
            user = userDAO.find(principal.getName());
            usersViews = usersViewsDAO.find(user, book);
        } catch (Exception ignored){}
        if (usersViews == null && user != null) {
            usersViews = new UsersViews();
            usersViews.setBook(book);
            usersViews.setUser(user);
            usersViews.setViews(1L);
            usersViewsDAO.save(usersViews);
        } else if (user != null) {
            usersViewsDAO.increaseView(usersViews);
        }
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public List<UsersViews> findUsersViews(User user){
        return usersViewsDAO.find(user);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void deleteUsersViews(Book book){
        usersViewsDAO.delete(book);
    }

    private void encodeUserPass(User user){
        String hashedPassword = bCrypt.encode(user.getPassword());
        user.setPassword(hashedPassword);
    }

}
