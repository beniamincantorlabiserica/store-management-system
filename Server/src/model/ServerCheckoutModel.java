package model;

import java.rmi.RemoteException;

public interface ServerCheckoutModel {
    Item scanItem(String barCode) throws RemoteException;

    Double checkout() throws RemoteException;
}
