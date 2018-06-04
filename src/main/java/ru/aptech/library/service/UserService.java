package ru.aptech.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.dao.UserDAOImpl;
import ru.aptech.library.entities.User;

@Service
public class UserService {
    @Autowired
    private UserDAOImpl userDao;
    @Autowired
    protected BCryptPasswordEncoder bCrypt;


    public boolean save(User user) {
        try {
            encodeUserPass(user);
            user.fillUserRoles();
            userDao.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void encodeUserPass(User user){
        String hashedPassword = bCrypt.encode(user.getPassword());
        user.setPassword(hashedPassword);
    }

}
