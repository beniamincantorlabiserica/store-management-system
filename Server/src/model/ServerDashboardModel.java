package model;

import java.rmi.RemoteException;

public interface ServerDashboardModel {
    WorkingHours getWorkingHours();
    void setOpeningHours(String openingTime);
    void setClosingHours(String closingTime);
    String getCheckoutsThisMonth() throws RemoteException;
    String getItemsThisMonth() throws RemoteException;
    String getSalesThisMonth() throws RemoteException;
}
