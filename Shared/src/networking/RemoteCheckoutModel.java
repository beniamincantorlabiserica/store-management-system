package networking;

import model.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteCheckoutModel extends Remote {
    Item scanItem(String barCode) throws RemoteException;

    Double checkout() throws RemoteException;

    void cancelCheckout() throws RemoteException;

    void cancelCheckout(Integer checkoutId) throws RemoteException;
}
