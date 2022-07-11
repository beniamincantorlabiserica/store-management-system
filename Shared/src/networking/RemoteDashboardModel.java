package networking;

import model.WorkingHours;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteDashboardModel extends Remote {
    WorkingHours getWorkingHours() throws RemoteException;
    void setOpeningHours(String openingTime) throws RemoteException;
    void setClosingHours(String closingTime) throws RemoteException;
    String getCheckoutsToday() throws RemoteException;
    String getItemsToday() throws RemoteException;
    String getSalesToday() throws RemoteException;
    String getCheckoutsThisMonth() throws RemoteException;
    String getItemsThisMonth() throws RemoteException;
    String getSalesThisMonth() throws RemoteException;
}
