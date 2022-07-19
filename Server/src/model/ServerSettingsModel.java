package model;

import java.rmi.RemoteException;

public interface ServerSettingsModel {
    void setLockedState(boolean b) throws RemoteException;
    boolean getLockedState() throws RemoteException;
}
