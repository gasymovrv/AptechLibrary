package ru.aptech.library;

import org.hibernate.SessionFactory;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.dao.UserDAO;
import ru.aptech.library.dao.impl.AuthorDAOImpl;
import ru.aptech.library.dao.impl.BookDAOImpl;
import ru.aptech.library.dao.impl.GenreDAOImpl;
import ru.aptech.library.dao.impl.PublisherDAOImpl;
import ru.aptech.library.entities.*;
import ru.aptech.library.service.*;
import ru.aptech.library.util.Utils;

/**
 * @author Petri Kainulainen
 */
@Configuration
@ComponentScan(basePackages = {"ru.aptech.library.controllers"})
@EnableWebMvc
public class TestContext {
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }
    @Bean
    public UrlBasedViewResolver tilesViewResolver() {
        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
        urlBasedViewResolver.setViewClass(TilesView.class);
        return urlBasedViewResolver;
    }

    @Bean
    public BookService bookService() {
        return Mockito.mock(BookService.class);
    }
    @Bean
    public AuthorService authorService() {
        return Mockito.mock(AuthorService.class);
    }
    @Bean
    public GenreService genreService() {
        return Mockito.mock(GenreService.class);
    }
    @Bean
    public PublisherService publisherService() {
        return Mockito.mock(PublisherService.class);
    }
    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public UserDAO<User, String> userDAO() {
        return Mockito.mock(UserDAO.class);
    }

    @Bean
    public UserDAO<UsersViews, Long> usersViewsDAO() {
        return Mockito.mock(UserDAO.class);
    }

    @Bean
    public CommonDAO<Book> bookDAO() {
        return Mockito.mock(BookDAOImpl.class);
    }

    @Bean
    public CommonDAO<Author> authorDAO() {
        return Mockito.mock(AuthorDAOImpl.class);
    }

    @Bean
    public CommonDAO<Genre> genreDAO() {
        return Mockito.mock(GenreDAOImpl.class);
    }

    @Bean
    public CommonDAO<Publisher> publisherDAO() {
        return Mockito.mock(PublisherDAOImpl.class);
    }

    @Bean
    public SessionFactory sessionFactory() {
        return Mockito.mock(SessionFactory.class);
    }

    @Bean
    public BCryptPasswordEncoder bcrypt() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public Utils utils() {
        return new Utils();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }
}
