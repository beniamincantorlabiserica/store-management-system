package model;

public interface ServerUsersModel {
    User login(String password);
    void updatePassword(String password, String role);
}
