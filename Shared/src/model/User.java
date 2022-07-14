package model;

import java.io.Serializable;

public class User implements Serializable {
    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public boolean isMaster() {
        return role.equals("master");
    }
    public boolean isStoreManager() {
        return role.equals("storeManager");
    }

    public User(String role) {
        this.role = role;
    }
}