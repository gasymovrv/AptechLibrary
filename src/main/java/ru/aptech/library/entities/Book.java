package ru.aptech.library.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Book implements Serializable {
    private Long id;
    private String name;
    private byte[] content;
    private Integer pageCount;
    private String isbn;
    private Genre genre;
    private Author author;
    private Integer publishYear;
    private Publisher publisher;
    private byte[] image;
    private String descr;
    private String bookcol;
    private Integer rating;
    private Long voteCount;
    private LocalDateTime created;
    private Long views;
    private Double price;


    public Book() {
    }


    public Book(Long id,
                String name,
                Integer pageCount,
                String isbn,
                Genre genre,
                Author author,
                Integer publishYear,
                Publisher publisher,
                byte[] image,
                String descr,
                String bookcol,
                Integer rating,
                Long voteCount,
                Long views,
                Double price) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.publishYear = publishYear;
        this.publisher = publisher;
        this.image = image;
        this.descr = descr;
        this.bookcol = bookcol;
        this.rating = rating;
        this.voteCount = voteCount;
        this.views = views;
        this.price = price;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public byte[] getContent() {
        return content;
    }


    public void setContent(byte[] content) {
        this.content = content;
    }


    public Integer getPageCount() {
        return pageCount;
    }


    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }


    public String getIsbn() {
        return isbn;
    }


    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public Genre getGenre() {
        return genre;
    }


    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    public Author getAuthor() {
        return author;
    }


    public void setAuthor(Author author) {
        this.author = author;
    }


    public Publisher getPublisher() {
        return publisher;
    }


    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }


    public Integer getPublishYear() {
        return publishYear;
    }


    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }


    public byte[] getImage() {
        return image;
    }


    public void setImage(byte[] image) {
        this.image = image;
    }


    public String getDescr() {
        return descr;
    }


    public void setDescr(String descr) {
        this.descr = descr;
    }


    public String getBookcol() {
        return bookcol;
    }


    public void setBookcol(String bookcol) {
        this.bookcol = bookcol;
    }


    public Integer getRating() {
        return rating;
    }


    public void setRating(Integer rating) {
        this.rating = rating;
    }


    public Long getVoteCount() {
        return voteCount;
    }


    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }


    public LocalDateTime getCreated() {
        return created;
    }


    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


    public Long getViews() {
        return views;
    }


    public void setViews(Long views) {
        this.views = views == null ? 0L : views;
    }


    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) &&
                pageCount.equals(book.pageCount) &&
                genre.equals(book.genre) &&
                author.equals(book.author) &&
                publishYear.equals(book.publishYear) &&
                publisher.equals(book.publisher) &&
                Objects.equals(name, book.name) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(descr, book.descr) &&
                Objects.equals(bookcol, book.bookcol) &&
                Objects.equals(rating, book.rating) &&
                Objects.equals(voteCount, book.voteCount) &&
                Objects.equals(views, book.views) &&
                Objects.equals(price, book.price);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, pageCount, isbn, genre, author, publishYear, publisher, descr, bookcol, rating, voteCount, views, price);
        result = 31 * result;
        return result;
    }

    public void setAllField(Book book) {
        this.name = book.name;
        if(book.content!=null && book.content.length>0){
            this.content = book.content;
        }
        this.pageCount = book.pageCount;
        this.isbn = book.isbn;
        this.genre = book.genre;
        this.author = book.author;
        this.publishYear = book.publishYear;
        this.publisher = book.publisher;
        if(book.image!=null && book.image.length>0){
            this.image = book.image;
        }
        this.descr = book.descr;
        this.bookcol = book.bookcol;
        this.rating = book.rating;
        this.voteCount = book.voteCount;
    }
}
