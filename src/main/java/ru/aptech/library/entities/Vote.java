package ru.aptech.library.entities;

import java.io.Serializable;
import java.util.Objects;

public class Vote implements Serializable {
    private Long id;
    private Integer value;
    private Long bookId;
    private String username;


    public Vote() {
    }


    public Vote(Long id, Integer value, Long bookId, String username) {
        this.id = id;
        this.value = value;
        this.bookId = bookId;
        this.username = username;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Integer getValue() {
        return value;
    }


    public void setValue(Integer value) {
        this.value = value;
    }


    public Long getBookId() {
        return bookId;
    }


    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return id.equals(vote.id) &&
                bookId.equals(vote.bookId) &&
                Objects.equals(value, vote.value) &&
                Objects.equals(username, vote.username);
    }


    @Override
    public int hashCode() {

        return Objects.hash(id, value, bookId, username);
    }
}
