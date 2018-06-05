package ru.aptech.library.controllers;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.entities.User;
import ru.aptech.library.entities.UserRole;
import ru.aptech.library.entities.UsersViews;
import ru.aptech.library.enums.RoleType;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("users/")
public class UserController extends BaseController{

    @RequestMapping(value = "authorization", method = RequestMethod.GET)
    public ModelAndView authorization(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("login-page");
        if (error != null) {
            modelAndView.addObject("error", "Неверное имя пользователя или пароль!");
        }
        return modelAndView;
    }

    @RequestMapping(value = "registrationView", method = RequestMethod.GET)
    public ModelAndView registrationView() {
        ModelAndView modelAndView = new ModelAndView("registration-page");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("userRoles", RoleType.values());
        return modelAndView;
    }

    @RequestMapping(value = "registrationAction", method = RequestMethod.POST)
    public ModelAndView registrationAction(@ModelAttribute("user") User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("login-page");
        boolean isAdded = userService.save(user);
        modelAndView.addObject("isAdded", isAdded);
        modelAndView.addObject("username", user.getUsername());
        return modelAndView;
    }

    @RequestMapping(value = "account", method = RequestMethod.GET)
    public ModelAndView account(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("account-page");
        User user = userService.findByUserName(principal.getName());
        List<UsersViews> usersViews = userService.findUsersViews(user);
        modelAndView.addObject("user", user);
        modelAndView.addObject("usersViews", usersViews);
        modelAndView.addObject("activeTab", "info");
        return modelAndView;
    }



    /**
     * Методы для работы с ajax
     * */
    @RequestMapping(value = "isAdmin", method = RequestMethod.GET)
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
    @RequestMapping(value = "isUser", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isUser(Authentication authentication) {
        boolean result = false;
        if(authentication != null){
            for (GrantedAuthority g : authentication.getAuthorities()) {
                if(g.getAuthority().equals("ROLE_USER")){
                    result = true;
                }
            }
        }
        return result;
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
