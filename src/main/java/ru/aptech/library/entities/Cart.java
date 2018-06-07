package ru.aptech.library.entities;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
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


    public void removeBook(Long id){
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book b = iter.next();
            if(b.getId().equals(id)){
                b.getCarts().remove(this);
                iter.remove();
            }
        }
    }

    public void removeAllBooks(){
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book b = iter.next();
            b.getCarts().remove(this);
            iter.remove();
        }
    }


    @Transient
    public double getSum(){
        double sumCart = 0;
        for (Book b : books) {
            sumCart += b.getPrice();
        }
        return sumCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(username, cart.username) &&
                Objects.equals(user, cart.user);
    }


    @Override
    public int hashCode() {
        return Objects.hash(username, user);
    }
}
