package model;

public interface ServerModel extends ServerUsersModel, ServerItemModel, ServerDashboardModel, ServerSettingsModel, ServerInventoryModel, ServerCheckoutModel {
    void powerOff();

    void softRestart();
}
