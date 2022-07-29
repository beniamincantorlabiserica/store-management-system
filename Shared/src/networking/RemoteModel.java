package networking;

import java.rmi.Remote;

public interface RemoteModel extends RemoteItemModel, RemoteUsersModel, RemoteDashboardModel, RemoteSettingsModel, Remote, RemoteInventoryModel, RemoteCheckoutModel, RemoteCashierModel {
}
