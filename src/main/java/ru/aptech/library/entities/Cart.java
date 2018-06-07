package ru.aptech.library.entities;

import ru.aptech.library.enums.RoleType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Cart implements Serializable {
    private String username;
    private User user;
    private Set<Book> books = new HashSet<>(0);


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Set<Book> getBooks() {
        return books;
    }


    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
