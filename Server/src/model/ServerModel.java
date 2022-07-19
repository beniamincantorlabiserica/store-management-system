package model;

public interface ServerModel extends ServerUsersModel, ServerItemModel, ServerDashboardModel, ServerSettingsModel, ServerInventoryModel {
    void powerOff();

    void softRestart();
}
