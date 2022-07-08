package model;

import java.rmi.RemoteException;

public interface UserModel {
    boolean login(String password) throws RemoteException;
}
