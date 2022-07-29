package networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSettingsModel extends Remote {
    void setLockedState(boolean b) throws RemoteException;
    boolean getLockedState() throws RemoteException;
}
