package model;

import java.rmi.RemoteException;

public interface ServerSettingsModel {
    /**
     * @return the locked state of the store, true is locked, false is unlocked
     * @throws RemoteException if there is a fetch-from-database error
     */
    boolean getLockedState() throws RemoteException;

    /**
     * @param b expects a boolean to represent the new status of the store where
     *          true is locked, false is unlocked
     */
    void setLockedState(boolean b) throws RemoteException;
}
