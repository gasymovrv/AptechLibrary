package ru.aptech.library.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

//@Entity
public class Publisher implements Serializable {
    private long id;
    private String name;


    public Publisher() {
    }


    public Publisher(long id, String name) {
        this.id = id;
        this.name = name;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return id == publisher.id &&
                Objects.equals(name, publisher.name);
    }


    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
