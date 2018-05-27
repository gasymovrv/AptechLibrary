package ru.aptech.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.dao.UserDAOImpl;
import ru.aptech.library.dao.UserRoleDAOImpl;
import ru.aptech.library.entities.User;
import ru.aptech.library.entities.UserRole;
import ru.aptech.library.enums.RoleType;

import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
    @Autowired
    private UserDAOImpl userDAO;
    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @RequestMapping(value = "/users/authorization", method = RequestMethod.GET)
    public ModelAndView authorization(@RequestParam(value = "error", required = false) String error, Authentication authentication, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("login-page");
        if (error != null) {
            modelAndView.addObject("error", "Неверное имя пользователя или пароль!");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/users/registrationView", method = RequestMethod.GET)
    public ModelAndView registrationView() {
        ModelAndView modelAndView = new ModelAndView("registration-page");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("userRoles", RoleType.values());
        return modelAndView;
    }

    @RequestMapping(value = "/users/registrationAction", method = RequestMethod.POST)
    public ModelAndView registrationAction(@ModelAttribute("user") User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("login-page");
        encodeUserPass(user);
        boolean isAdded;
        try {
            userDAO.save(user);
            isAdded = true;
        } catch (Exception e) {
            isAdded = false;
        }
        modelAndView.addObject("isAdded", isAdded);
        modelAndView.addObject("username", user.getUsername());
        return modelAndView;
    }

    @RequestMapping(value = "/users/isAdmin", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isAdmin(Authentication authentication) {
        boolean result = false;
        if(authentication != null){
            for (GrantedAuthority g : authentication.getAuthorities()) {
                if(g.getAuthority().equals("ROLE_ADMIN")){
                    result = true;
                }
            }
        }
        return result;
    }

    private void encodeUserPass(User user){
        String hashedPassword = bCrypt.encode(user.getPassword());
        user.setPassword(hashedPassword);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, "userRole", new CustomCollectionEditor(Set.class)
        {
            @Override
            protected Object convertElement(Object element)
            {
                String role = null;
                if (element instanceof String) {
                    role = element.toString();
                }
                UserRole result = new UserRole();
                result.setRole(role);
                return role != null ? result: null;
            }
        });
    }
}
