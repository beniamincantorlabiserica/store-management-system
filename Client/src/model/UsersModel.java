package model;


import java.rmi.RemoteException;

public interface UsersModel {

    boolean login (String password) throws RemoteException;

}
