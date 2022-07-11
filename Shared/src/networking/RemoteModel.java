package networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteModel extends RemoteItemModel, RemoteUsersModel, RemoteDashboardModel, Remote {
}
