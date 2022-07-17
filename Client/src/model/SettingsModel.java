package model;

import java.rmi.RemoteException;

public interface SettingsModel {
    void setLockedState(boolean b);
    boolean getLockedState() throws RemoteException;
}
