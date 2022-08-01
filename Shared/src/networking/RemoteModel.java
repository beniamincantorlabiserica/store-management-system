package networking;

import java.rmi.Remote;

public interface RemoteModel extends RemoteUsersModel, RemoteDashboardModel, RemoteSettingsModel, Remote, RemoteInventoryModel, RemoteCheckoutModel, RemoteCashierModel {
}
