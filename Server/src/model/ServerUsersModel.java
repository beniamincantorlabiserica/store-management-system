package model;

public interface ServerUsersModel {
    User login (String password);
    void changePassword (String password, String role);
}
