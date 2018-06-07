package ru.aptech.library.entities;

import ru.aptech.library.enums.RoleType;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.*;


public class User implements Serializable {
    private String username;
    private String password;
    private boolean enabled = true;
    private Set<UserRole> userRole = new HashSet<>(0);
    private Cart cart;
    private Set<Order> orders = new HashSet<>(0);
    private Double money;


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public Set<UserRole> getUserRole() {
        return userRole;
    }


    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }


    public Cart getCart() {
        return cart;
    }


    public void setCart(Cart cart) {
        this.cart = cart;
    }


    public Set<Order> getOrders() {
        return orders;
    }


    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }


    public Double getMoney() {
        return money;
    }


    public void setMoney(Double money) {
        this.money = money;
    }


    @Transient
    public Set<Order> getSortedOrders() {
        //создаем сет, сортирующий по убаванию даты из поля created
        Set<Order> sortedSet = new TreeSet<>((e1, e2) -> e2.getCreated().compareTo(e1.getCreated()));
        sortedSet.addAll(orders);
        return sortedSet;
    }


    public void fillUserRoles(){
        if (!userRole.isEmpty()) {
            for (UserRole ur : userRole) {
                ur.setUser(this);
            }
        } else {
            UserRole ur = new UserRole();
            ur.setUser(this);
            ur.setRole(RoleType.ROLE_USER.toString());
            userRole.add(ur);
        }
    }
}
