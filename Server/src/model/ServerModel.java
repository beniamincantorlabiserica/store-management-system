package model;

import database.ManagerFactory;
import networking.RemoteSettingsModel;

import java.rmi.RemoteException;

public interface ServerModel extends ServerUsersModel, ServerItemModel, ServerDashboardModel, ServerSettingsModel {
    void powerOff();

    void softRestart();
}
