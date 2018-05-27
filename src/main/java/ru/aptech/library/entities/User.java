package ru.aptech.library.entities;

import ru.aptech.library.enums.RoleType;

import java.util.HashSet;
import java.util.Set;


public class User {
    private String username;
    private String password;
    private boolean enabled = true;
    private Set<UserRole> userRole = new HashSet<UserRole>(0);


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
