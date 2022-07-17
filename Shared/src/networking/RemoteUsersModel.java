package networking;



import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteUsersModel extends Remote {
    User login (String password) throws RemoteException;
    void updatePassword(String role, String password) throws RemoteException;
    String getMasterPassword() throws RemoteException;
}
