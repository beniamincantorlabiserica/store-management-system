package networking;

import model.WorkingHours;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteDashboardModel extends Remote {
    /**
     * @return the current working hours (open/close schedule) as a WorkingHours object
     */
    WorkingHours getWorkingHours() throws RemoteException;

    /**
     * @param openingTime expects a String with the format of "HH:mm" to represent the new
     *                    opening time for the store
     * @throws RuntimeException if the format of the String is invalid or if the desired
     *                          opening hours are after the closing hours
     */
    void setOpeningHours(String openingTime) throws RemoteException;

    /**
     * @param closingTime expects a String with the format of "HH:mm" to represent the new
     *                    closing time for the store
     * @throws RuntimeException if the format of the String is invalid or if the desired
     *                          closing hours are before the opening hours
     */
    void setClosingHours(String closingTime) throws RemoteException;

    /**
     * @return the number of checkouts having the date set to the current day
     */
    String getCheckoutsToday() throws RemoteException;

    /**
     * @return the number of items * quantity from the checkouts having the date set to the current day
     */
    String getItemsToday() throws RemoteException;

    /**
     * @return the income given all the items * quantity from the checkouts having the date set to the current day
     */
    String getSalesToday() throws RemoteException;

    /**
     * @return the number of checkouts having the date within 30 days in the past from the current day
     */
    String getCheckoutsThisMonth() throws RemoteException;

    /**
     * @return the number of items * quantity from the checkouts having the date within 30 days in the past from the current day
     */
    String getItemsThisMonth() throws RemoteException;

    /**
     * @return the income given all the items * quantity checkouts having the date within 30 days in the past from the current day
     */
    String getSalesThisMonth() throws RemoteException;
}
