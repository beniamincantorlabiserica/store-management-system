package model;

import java.rmi.RemoteException;

public interface Model extends ItemModel, UserModel, DashboardModel {
    boolean isNetwork();
    void retryConnection() throws RuntimeException;
}
