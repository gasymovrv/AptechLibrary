package ru.aptech.library.controllers;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aptech.library.entities.*;
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
        ModelAndView modelAndView = new ModelAndView();
        boolean isAdded;
        try {
            userService.save(user);
            isAdded = true;
            modelAndView.setViewName("login-page");
            modelAndView.addObject("username", user.getUsername());
        } catch (Exception e){
            isAdded = false;
            modelAndView.setViewName("registration-page");
            modelAndView.addObject("user", new User());
            modelAndView.addObject("userRoles", RoleType.values());
            modelAndView.addObject("username", user.getUsername());
            e.printStackTrace();
        }
        modelAndView.addObject("isAdded", isAdded);
        return modelAndView;
    }

    @RequestMapping(value = "account", method = RequestMethod.GET)
    public ModelAndView account(@RequestParam(required = false) String tab,
                                @RequestParam(required = false) Boolean addMoney,
                                @RequestParam(required = false) Long delBookFromCart,
                                @RequestParam(required = false) Boolean buy,
                                @RequestParam(required = false) Boolean clearCart,
                                Principal principal) {
        ModelAndView modelAndView = new ModelAndView("account-page");
        User user = userService.find(principal.getName(), "booksInCart", "booksInOrders");
        if(addMoney!=null && addMoney){
            user.setMoney(user.getMoney()+1000);//просто увеличиваем баланс на 1000 пока нет системы оплаты
            userService.update(user);
        }
        if(delBookFromCart!=null){
            user.getCart().removeBook(delBookFromCart);
            userService.update(user);
        }

        Boolean enoughMoney = null;
        Boolean successBuy = null;
        if(buy!=null && buy && !user.getCart().getBooks().isEmpty()){
            enoughMoney = user.getCart().getSum() <= user.getMoney();
            if(enoughMoney) {
                try {
                    userService.buyBooksFromCart(user);
                    successBuy = true;
                } catch (Exception e) {
                    successBuy = false;
                    e.printStackTrace();
                }
            }
        }
        if(clearCart!=null && clearCart){
            user.getCart().removeAllBooks();
            userService.update(user);
        }
        List<UsersViews> usersViews = userService.findUsersViews(user);
        modelAndView.addObject("enoughMoney", enoughMoney);
        modelAndView.addObject("successBuy", successBuy);
        modelAndView.addObject("user", user);
        modelAndView.addObject("usersViews", usersViews);
        modelAndView.addObject("activeTab", !StringUtils.isEmpty(tab) ? tab : "info");
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
    @RequestMapping(value = "checkBuyBook", method = RequestMethod.GET)
    @ResponseBody
    public Boolean checkBuyBook(@RequestParam Long bookId, Authentication authentication) {
        boolean result = false;
        if(authentication != null){
            Book book = bookService.find(bookId, false);
            User user = userService.find(authentication.getName(), "booksInOrders");
            for(Order o : user.getOrders()){
                if(o.getBooks().contains(book)){
                    result = true;
                }
            }
        }
        return result;
    }
    @RequestMapping(value = "checkBookInCart", method = RequestMethod.GET)
    @ResponseBody
    public Boolean checkBookInCart(@RequestParam Long bookId, Authentication authentication) {
        boolean result = false;
        if(authentication != null){
            Book book = bookService.find(bookId, false);
            User user = userService.find(authentication.getName(), "booksInCart");
            if (user.getCart().getBooks().contains(book)) {
                result = true;
            }
        }
        return result;
    }
    @RequestMapping(value = "checkBookInOrders", method = RequestMethod.GET)
    @ResponseBody
    public Boolean checkBookInOrders(@RequestParam Long bookId) {
        Book book = bookService.find(bookId, true);
        return book.getOrders()!=null && !book.getOrders().isEmpty();
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
