package ru.aptech.library.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {

    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public ModelAndView authorization(@RequestParam(value = "error", required = false) String error, Authentication authentication, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("login-page");
        if (error != null) {
            modelAndView.addObject("error", "Неверное имя пользователя или пароль!");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/user/isAdmin", method = RequestMethod.GET)
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
}
