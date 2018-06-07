package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.dao.UserDAOImpl;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.Cart;
import ru.aptech.library.entities.User;
import ru.aptech.library.entities.UsersViews;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAOImpl userDao;
    @Autowired
    protected BCryptPasswordEncoder bCrypt;


    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(User user) {
        encodeUserPass(user);
        user.fillUserRoles();
        userDao.save(user);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void updateCart(Cart cart) {
        userDao.updateCart(cart);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void saveOrIncreaseUsersViews(Principal principal, Book book) {
        User user = null;
        UsersViews usersViews = null;
        try {
            user = userDao.findByUserName(principal.getName());
            usersViews = userDao.findUsersViews(user, book);
        } catch (Exception ignored){}
        if (usersViews == null && user != null) {
            usersViews = new UsersViews();
            usersViews.setBook(book);
            usersViews.setUser(user);
            usersViews.setViews(1L);
            userDao.saveUsersViews(usersViews);
        } else if (user != null) {
            userDao.increaseView(usersViews);
        }
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public List<UsersViews> findUsersViews(User user){
        return userDao.findUsersViews(user);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void deleteUsersViews(Book book){
        userDao.deleteUsersViews(book);
    }

    private void encodeUserPass(User user){
        String hashedPassword = bCrypt.encode(user.getPassword());
        user.setPassword(hashedPassword);
    }

}
