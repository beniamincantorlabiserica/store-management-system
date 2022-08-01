package model;

public interface ServerModel extends ServerUsersModel, ServerDashboardModel, ServerSettingsModel, ServerInventoryModel, ServerCheckoutModel, ServerCashierModel {
    /**
     * attempts to restart the server <-> database connection and to update the server without
     * closing the RMI connection with the clients
     */
    void softRestart();
}
