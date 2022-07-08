package model;

import org.json.JSONObject;

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

    public User(String role) {
        this.role = role;
    }
}