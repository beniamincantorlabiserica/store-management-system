package networking;

import model.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteInventoryModel extends Remote {
    ArrayList<Item> getItems() throws RemoteException;
}
