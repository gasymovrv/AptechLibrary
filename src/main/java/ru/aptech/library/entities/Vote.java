package ru.aptech.library.entities;

import java.io.Serializable;
import java.util.Objects;

//@Entity
public class Vote implements Serializable {
    private long id;
    private Integer value;
    private long bookId;
    private String username;


    public Vote() {
    }


    public Vote(long id, Integer value, long bookId, String username) {
        this.id = id;
        this.value = value;
        this.bookId = bookId;
        this.username = username;
    }


    //    @Id
//    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    //    @Basic
//    @Column(name = "value", nullable = true)
    public Integer getValue() {
        return value;
    }


    public void setValue(Integer value) {
        this.value = value;
    }


    //    @Basic
//    @Column(name = "book_id", nullable = false)
    public long getBookId() {
        return bookId;
    }


    public void setBookId(long bookId) {
        this.bookId = bookId;
    }


    //    @Basic
//    @Column(name = "username", nullable = false, length = 100)
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
        return id == vote.id &&
                bookId == vote.bookId &&
                Objects.equals(value, vote.value) &&
                Objects.equals(username, vote.username);
    }


    @Override
    public int hashCode() {

        return Objects.hash(id, value, bookId, username);
    }
}
