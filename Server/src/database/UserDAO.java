package database;

import model.User;

public interface UserDAO {
    User login(String password);

    String getMasterPassword();

    void updatePassword(String role, String password) throws RuntimeException;
}
