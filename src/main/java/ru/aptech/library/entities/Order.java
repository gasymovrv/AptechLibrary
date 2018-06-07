package ru.aptech.library.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


public class Order implements Serializable{
    private Long id;
    private User user;
    private Set<Book> books = new HashSet<>(0);
    private LocalDateTime created;


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


    public Set<Book> getBooks() {
        return books;
    }


    public void setBooks(Set<Book> books) {
        this.books = books;
    }


    public LocalDateTime getCreated() {
        return created;
    }


    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
