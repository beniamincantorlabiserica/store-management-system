package model;

public interface ServerModel extends ServerUsersModel, ServerItemModel, ServerDashboardModel, ServerSettingsModel, ServerInventoryModel, ServerCheckoutModel, ServerCashierModel {
    void powerOff();

    void softRestart();
}
