package server.model.classes;

import org.json.JSONObject;

public class User {

    public int id;
    public String password;
    public String role;

    public User(int id, String password, String role) {
        this.id = id;

        this.password = password;

        this.role = role;
    }

    public User() {

    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}