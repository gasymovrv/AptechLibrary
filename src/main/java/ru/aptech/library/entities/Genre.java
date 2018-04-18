package ru.aptech.library.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

//@Entity
public class Genre implements Serializable {
    private long id;
    private String name;
    private Long parent;


    public Genre() {
    }


    public Genre(long id, String name, Long parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
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
//    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


//    @Basic
//    @Column(name = "parent", nullable = true)
    public Long getParent() {
        return parent;
    }


    public void setParent(Long parent) {
        this.parent = parent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id == genre.id &&
                Objects.equals(name, genre.name) &&
                Objects.equals(parent, genre.parent);
    }


    @Override
    public int hashCode() {

        return Objects.hash(id, name, parent);
    }
}
