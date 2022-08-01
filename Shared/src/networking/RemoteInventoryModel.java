package networking;

import model.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteInventoryModel extends Remote {
    /**
     * @return an ArrayList of the current Items from the database
     */
    ArrayList<Item> getItems() throws RemoteException;

    void changePrice(Long id, Double price) throws RemoteException;

    void updateQuantity(int id, int quantity) throws RemoteException;
}
