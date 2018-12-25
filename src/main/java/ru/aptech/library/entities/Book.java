package ru.aptech.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Book implements Serializable {
    private Long id;
    private String name;
    /**
     * Сделано отношением one-to-many для ленивой загрузки,
     * улучшения производительности и уменьшения затрат памяти
     * (для one-to-one - lazy не работает,
     * а поле Blob всегда загружается - это возможно проблема MySQL)
     * */
    @JsonIgnore
    private Set<BookContent> bookContents = new HashSet<>();
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
    @JsonIgnore
    private Set<Cart> carts = new HashSet<>(0);
    @JsonIgnore
    private Set<Order> orders = new HashSet<>(0);
    /**
     * Всегда равно пустой строке если контент не загружен
     * */
    private String fileExtension;
    /**
     * Всегда равно null если контент не загружен
     * */
    private String contentType;
    private String fileSize;


    public Book() {
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


    public Set<BookContent> getBookContents() {
        return bookContents;
    }


    public void setBookContents(Set<BookContent> bookContent) {
        this.bookContents = bookContent;
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


    public Set<Cart> getCarts() {
        return carts;
    }


    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }


    public Set<Order> getOrders() {
        return orders;
    }


    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }


    public String getFileExtension() {
        return fileExtension;
    }


    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }


    public String getContentType() {
        return contentType;
    }


    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public String getFileSize() {
        return fileSize;
    }


    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
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
                Objects.equals(price, book.price) &&
                Objects.equals(fileExtension, book.fileExtension) &&
                Objects.equals(contentType, book.contentType) &&
                Objects.equals(fileSize, book.fileSize);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, pageCount, isbn, genre, author, publishYear, publisher, descr, bookcol, rating, voteCount, views, price, fileExtension);
        result = 31 * result;
        return result;
    }

    public void setAllField(Book book) throws SQLException {
        this.name = book.name;
        if(book.bookContents !=null && !book.bookContents.isEmpty() && book.bookContents.iterator().next().getContent() != null){
            this.bookContents = book.bookContents;
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
        this.price = book.price;
    }
}
