package networking;

import model.WorkingHours;

import java.rmi.RemoteException;

public interface RemoteDashboardModel {
    WorkingHours getWorkingHours() throws RemoteException;
    void setOpeningHours(String openingTime) throws RemoteException;
    void setClosingHours(String closingTime) throws RemoteException;
}
