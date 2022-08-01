package model;

public interface Model extends UserModel, DashboardModel, SettingsModel, StoreModel, InventoryModel, CashierModel {
    /**
     * @return true if the client could connect to the server, false otherwise
     */
    boolean isNetwork();

    /**
     * retries to connect to the server
     *
     * @throws RuntimeException if retrying connection fails again
     */
    void retryConnection() throws RuntimeException;
}
