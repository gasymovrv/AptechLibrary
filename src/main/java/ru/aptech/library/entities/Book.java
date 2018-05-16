package ru.aptech.library.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

//@Entity
public class Book implements Serializable {
    private long id;
    private String name;
    private byte[] content;
    private int pageCount;
    private String isbn;
    private Genre genre;
    private Author author;
    private int publishYear;
    private Publisher publisher;
    private byte[] image;
    private String descr;
    private String bookcol;
    private Integer rating;
    private Long voteCount;


    public Book() {
    }


    public Book(long id, String name, int pageCount, String isbn, Genre genre, Author author, int publishYear, Publisher publisher, byte[] image, String descr, String bookcol, Integer rating, Long voteCount) {
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
//    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    //    @Basic
//    @Column(name = "content", nullable = false)
    public byte[] getContent() {
        return content;
    }


    public void setContent(byte[] content) {
        this.content = content;
    }


    //    @Basic
//    @Column(name = "page_count", nullable = false)
    public int getPageCount() {
        return pageCount;
    }


    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


    //    @Basic
//    @Column(name = "isbn", nullable = false, length = 100)
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


    //    @Basic
//    @Column(name = "publish_year", nullable = false)
    public int getPublishYear() {
        return publishYear;
    }


    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }


    //    @Basic
//    @Column(name = "image", nullable = true)
    public byte[] getImage() {
        return image;
    }


    public void setImage(byte[] image) {
        this.image = image;
    }


    //    @Basic
//    @Column(name = "descr", nullable = true, length = 5000)
    public String getDescr() {
        return descr;
    }


    public void setDescr(String descr) {
        this.descr = descr;
    }


    //    @Basic
//    @Column(name = "bookcol", nullable = true, length = 45)
    public String getBookcol() {
        return bookcol;
    }


    public void setBookcol(String bookcol) {
        this.bookcol = bookcol;
    }


    //    @Basic
//    @Column(name = "rating", nullable = true)
    public Integer getRating() {
        return rating;
    }


    public void setRating(Integer rating) {
        this.rating = rating;
    }


    //    @Basic
//    @Column(name = "vote_count", nullable = true)
    public Long getVoteCount() {
        return voteCount;
    }


    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                pageCount == book.pageCount &&
                genre == book.genre &&
                author == book.author &&
                publishYear == book.publishYear &&
                publisher == book.publisher &&
                Objects.equals(name, book.name) &&
                Arrays.equals(content, book.content) &&
                Objects.equals(isbn, book.isbn) &&
                Arrays.equals(image, book.image) &&
                Objects.equals(descr, book.descr) &&
                Objects.equals(bookcol, book.bookcol) &&
                Objects.equals(rating, book.rating) &&
                Objects.equals(voteCount, book.voteCount);
    }


    @Override
    public int hashCode() {

        int result = Objects.hash(id, name, pageCount, isbn, genre, author, publishYear, publisher, descr, bookcol, rating, voteCount);
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + Arrays.hashCode(image);
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
