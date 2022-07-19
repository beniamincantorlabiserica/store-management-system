package model;

public interface Model extends ItemModel, UserModel, DashboardModel, SettingsModel, StoreModel, InventoryModel {
    boolean isNetwork();
    void retryConnection() throws RuntimeException;
}
