package model;

public interface ServerModel extends ServerUsersModel, ServerDashboardModel, ServerSettingsModel, ServerInventoryModel, ServerCheckoutModel, ServerCashierModel {
    void powerOff();

    void softRestart();
}
