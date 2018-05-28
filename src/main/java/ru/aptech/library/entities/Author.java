package ru.aptech.library.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.Objects;

//@Entity
//@Table(name = "author")
public class Author implements Serializable {
    private long id;
    private String fio;
    private LocalDate birthday;
    private Timestamp created;


    public Author() {
    }


    public Author(long id, String fio, LocalDate birthday) {
        this.id = id;
        this.fio = fio;
        this.birthday = birthday;
    }


    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


//    @Basic
//    @Column(name = "fio", nullable = false, length = 300)
    public String getFio() {
        return fio;
    }


    public void setFio(String fio) {
        this.fio = fio;
    }


//    @Basic
//    @Column(name = "birthday", nullable = false)
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
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
    }
}
