package model;

public interface Model extends ItemModel, UserModel, DashboardModel, SettingsModel, StoreModel, InventoryModel, CashierModel {
    /**
     * @return true if the client could connect to the server, false otherwise
     */
    boolean isNetwork();

    /**
     * @throws RuntimeException if retrying connection fails again
     */
    void retryConnection() throws RuntimeException;
}
