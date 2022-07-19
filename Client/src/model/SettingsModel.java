package model;

import java.rmi.RemoteException;

public interface SettingsModel {
    void setLockedState(boolean b);
    boolean getLockedState() throws RemoteException;
    void setOpeningHours(String openingTime) throws RuntimeException;
    void setClosingHours(String closingTime) throws RuntimeException;
}
