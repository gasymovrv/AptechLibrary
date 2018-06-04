package ru.aptech.library.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class Genre implements Serializable {
    private Long id;
    private String name;
    private Long parent;


    public Genre() {
    }


    public Genre(Long id, String name, Long parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
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
        return id.equals(genre.id) &&
                Objects.equals(name, genre.name) &&
                Objects.equals(parent, genre.parent);
    }


    @Override
    public int hashCode() {

        return Objects.hash(id, name, parent);
    }
}
