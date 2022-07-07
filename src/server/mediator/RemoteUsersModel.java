package server.mediator;



import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteUsersModel extends Remote {

    boolean login (String password) throws RemoteException;

}
