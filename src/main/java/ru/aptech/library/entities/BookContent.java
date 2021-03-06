package ru.aptech.library.entities;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Objects;

public class BookContent implements Serializable {
    private Long id;
    private Book book;
    private Blob content;


    public BookContent() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Book getBook() {
        return book;
    }


    public void setBook(Book book) {
        this.book = book;
    }


    public Blob getContent() {
        return content;
    }


    public void setContent(Blob content) {
        this.content = content;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookContent that = (BookContent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(book, that.book);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, book);
    }
}
