package model;

import java.rmi.RemoteException;

public interface SettingsModel {
    /**
     * @return the locked state of the store, true is locked, false is unlocked
     * @throws RemoteException if there is a fetch-from-database error
     */
    boolean getLockedState() throws RemoteException;

    /**
     * @param b expects a boolean to represent the new status of the store where
     *          true is locked, false is unlocked
     */
    void setLockedState(boolean b);

    /**
     * @param openingTime expects a String with the format of "HH:mm" to represent the new
     *                    opening time for the store
     * @throws RuntimeException if the format of the String is invalid or if the desired
     *                          opening hours are after the closing hours
     */
    void setOpeningHours(String openingTime) throws RuntimeException;

    /**
     * @param closingTime expects a String with the format of "HH:mm" to represent the new
     *                    closing time for the store
     * @throws RuntimeException if the format of the String is invalid or if the desired
     *                          closing hours are before the opening hours
     */
    void setClosingHours(String closingTime) throws RuntimeException;
}
