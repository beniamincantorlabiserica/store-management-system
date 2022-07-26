package model;

public interface Model extends ItemModel, UserModel, DashboardModel, SettingsModel, StoreModel, InventoryModel, CashierModel {
    boolean isNetwork();

    void retryConnection() throws RuntimeException;
}
