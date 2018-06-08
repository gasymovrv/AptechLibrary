package ru.aptech.library.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.aptech.library.entities.Book;
import ru.aptech.library.entities.User;

import java.util.List;

public interface UserDAO<T, PKType> extends DAO<T, PKType> {
    @Transactional
    default T find(User user, Book book){
        throw new UnsupportedOperationException();
    }

    @Transactional
    default List<T> find(User user){
        throw new UnsupportedOperationException();
    }

    @Transactional
    default void delete(Book b){throw new UnsupportedOperationException();}

    @Transactional
    default void increaseView(T o){throw new UnsupportedOperationException();}
}
