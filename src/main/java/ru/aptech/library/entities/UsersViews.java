package ru.aptech.library.entities;

import java.io.Serializable;

public class UsersViews implements Serializable {
    private Long id;
    private User user;
    private Book book;
    private Long views;


    public UsersViews() {
    }


    public UsersViews(Long id, User user, Book book) {
        this.id = id;
        this.user = user;
        this.book = book;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Book getBook() {
        return book;
    }


    public void setBook(Book book) {
        this.book = book;
    }


    public Long getViews() {
        return views;
    }


    public void setViews(Long views) {
        this.views = views;
    }
}
