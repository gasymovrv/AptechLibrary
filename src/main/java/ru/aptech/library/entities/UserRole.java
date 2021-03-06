package ru.aptech.library.entities;

import javax.persistence.*;
import java.io.Serializable;

public class UserRole implements Serializable {
    private Integer userRoleId;
    private User user;
    private String role;


    public Integer getUserRoleId() {
        return userRoleId;
    }


    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }

    //getter and setter methods
}
