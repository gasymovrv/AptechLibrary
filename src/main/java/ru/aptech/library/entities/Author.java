package ru.aptech.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Author implements Serializable {
    private Long id;
    private String fio;
    private LocalDate birthday;
    private Timestamp created;
    @JsonIgnore
    private Set<Book> books = new HashSet<>(0);


    public Author() {
    }


    public Author(Long id, String fio, LocalDate birthday) {
        this.id = id;
        this.fio = fio;
        this.birthday = birthday;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getFio() {
        return fio;
    }


    public void setFio(String fio) {
        this.fio = fio;
    }


    public LocalDate getBirthday() {
        return birthday;
    }


    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }


    public Timestamp getCreated() {
        return created;
    }


    public void setCreated(Timestamp created) {
        this.created = created;
    }


    public Set<Book> getBooks() {
        return books;
    }


    public void setBooks(Set<Book> books) {
        this.books = books;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id.equals(author.id) &&
                Objects.equals(fio, author.fio) &&
                Objects.equals(birthday, author.birthday);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, fio, birthday);
    }

    public void setAllField(Author author) {
        this.fio = author.fio;
        this.birthday = author.birthday;
        this.books = author.books;

    }
}
