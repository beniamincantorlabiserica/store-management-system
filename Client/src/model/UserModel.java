package model;

import java.rmi.RemoteException;

public interface UserModel {
    User login(String password) throws RuntimeException;
    void logout();
    boolean isLoggedIn();
}