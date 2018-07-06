package ru.aptech.library.service;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.aptech.library.dao.CommonDAO;
import ru.aptech.library.dao.UserDAO;
import ru.aptech.library.entities.*;
import ru.aptech.library.enums.SortType;
import ru.aptech.library.util.SearchCriteriaBooks;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class BookService {
    @Autowired
    @Qualifier("bookDAO")
    private CommonDAO<Book> bookDAO;
    @Autowired
    @Qualifier("authorDAO")
    private CommonDAO<Author> authorDAO;
    @Autowired
    @Qualifier("usersViewsDAO")
    private UserDAO<UsersViews, Long> usersViewsDAO;
    @Autowired
    @Qualifier("userDAO")
    private UserDAO<User, String> userDAO;
    @Autowired
    protected UserService userService;

    @Transactional(propagation= Propagation.REQUIRED)
    public List<Book> find(Boolean initSets) {
        List<Book> allBooks = bookDAO.find();
        if(initSets!=null && initSets){
            for (Book b : allBooks) {
                Hibernate.initialize(b.getOrders());
                Hibernate.initialize(b.getCarts());
            }
        }
        return allBooks;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Book find(Long id, Boolean initSets, Boolean noContent) {
        Book b;
        if(noContent!=null && noContent){
            b = bookDAO.find(id);
        } else {
            b = bookDAO.find(id);
            Hibernate.initialize(b.getBookContents());
        }
        if(initSets!=null && initSets){
            Hibernate.initialize(b.getOrders());
            Hibernate.initialize(b.getCarts());
        }
        return b;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public List<Book> find(SearchCriteriaBooks criteria, Integer booksOnPage, Integer selectedPage, SortType sortType) {
        int init = (selectedPage - 1) * booksOnPage;
        if (criteria != null && !criteria.isEmpty()) {
            return bookDAO.find(criteria, booksOnPage, init, sortType);
        }
        return bookDAO.find(booksOnPage, init, sortType);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public byte[] findImage(Long id) {
        return bookDAO.findBookImage(id);
    }

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(Book book, MultipartFile content, MultipartFile image) throws Exception {
        BookContent c = new BookContent();
        c.setBook(book);
        c.setContent(new javax.sql.rowset.serial.SerialBlob(content.getBytes()));
        book.getBookContents().add(c);

        book.setName(book.getName().replaceAll("[\"\']", ""));
        //если файл пуст, то ext=""
        String ext = FilenameUtils.getExtension(content.getOriginalFilename());
        book.setContentType(StringUtils.isEmpty(ext) ? null : content.getContentType());
        book.setFileExtension(ext);
        String fileSize;
        if(content.getSize()>1000000){
            fileSize = String.format(Locale.US, "%.2f %s", (double) content.getSize()/1000000D, "Мб");
        } else {
            fileSize = String.format(Locale.US, "%.2f %s", (double) content.getSize()/1000D, "Кб");
        }
        book.setFileSize(fileSize);
        book.setImage(image.getBytes());
        if(book.getViews()==null){book.setViews(0L);}
        book.setCreated(LocalDateTime.now());
        Long id = bookDAO.save(book);
        book.setAllField(bookDAO.find(id));
    }

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Book updatedBook, MultipartFile content, MultipartFile image, Long bookId) throws Exception {
        Book existBook = bookDAO.find(bookId);
        updatedBook.setName(updatedBook.getName().replaceAll("[\"\']", ""));

        //вытягиваем по id перзистентного автора и обновляем просмотры у старого и нового
        Author a = authorDAO.find(updatedBook.getAuthor().getId());
        a.setViews(a.getViews()+existBook.getViews());
        authorDAO.update(a);
        Author oldAuthor = existBook.getAuthor();
        oldAuthor.setViews(oldAuthor.getViews()-existBook.getViews());
        authorDAO.update(oldAuthor);

        updatedBook.setAuthor(a);
        existBook.setAllField(updatedBook);

        //Обновляем старый контент только если новый не пустой
        if (content != null && content.getSize() > 0) {
            //у созданной книги обязательно должен быть контент (может с пустым блобом но это не важно) поэтому проверки не нужны
            existBook.getBookContents().iterator().next().setContent(new javax.sql.rowset.serial.SerialBlob(content.getBytes()));
            String ext = FilenameUtils.getExtension(content.getOriginalFilename());
            existBook.setContentType(StringUtils.isEmpty(ext) ? null : content.getContentType());
            existBook.setFileExtension(ext);
            String fileSize;
            if(content.getSize()>1000000){
                fileSize = String.format(Locale.US, "%.2f %s", (double) content.getSize()/1000000D, "Мб");
            } else {
                fileSize = String.format(Locale.US, "%.2f %s", (double) content.getSize()/1000D, "Кб");
            }
            existBook.setFileSize(fileSize);
        }

        if (image != null && image.getSize() > 0) {
            existBook.setImage(image.getBytes());
        }

        bookDAO.update(existBook);
    }

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Long bookId) throws Exception {
        Book book = bookDAO.find(bookId);
        Author a = book.getAuthor();
        authorDAO.setViews(a.getId(),a.getViews()-book.getViews());
        List<User> users = userDAO.find();
        for (User u : users) {
            if (u.getCart().getBooks().contains(book)) {
                u.getCart().removeBook(book.getId());
                userService.update(u);
            }
        }
        usersViewsDAO.delete(book);
        bookDAO.deleteBookContents(book);
        bookDAO.delete(book);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Long getQuantityBooks(SearchCriteriaBooks criteria) {
        if (criteria != null && !criteria.isEmpty()) {
            return bookDAO.getQuantity(criteria);
        }
        return bookDAO.getQuantity();
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void increaseView(Long bookId){
        bookDAO.increaseView(bookId);
    }


    public CommonDAO<Book> getBookDAO() {
        return bookDAO;
    }


    public void setBookDAO(CommonDAO<Book> bookDAO) {
        this.bookDAO = bookDAO;
    }
}
