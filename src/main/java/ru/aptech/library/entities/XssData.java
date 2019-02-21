package ru.aptech.library.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class XssData {
    private Integer id;
    private String location;
    private String data;
    private Timestamp created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
        XssData xssData = (XssData) o;
        return id == xssData.id &&
                Objects.equals(location, xssData.location) &&
                Objects.equals(data, xssData.data) &&
                Objects.equals(created, xssData.created);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, location, data, created);
    }
}
