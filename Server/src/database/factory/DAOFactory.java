package database.factory;

import database.*;
import database.cache.DashboardCache;
import database.connection.DBConnection;
import database.manager.*;

public class DAOFactory implements DAOManager {
    private final UserDAO userDatabaseManager;
    private final DashboardDAO dashboardDatabaseManager;
    private final PreferenceDAO preferencesDatabaseManager;
    private final InventoryDAO inventoryDatabaseManager;
    private final CheckoutDAO checkoutDatabaseManager;

    public DAOFactory(DBConnection dbConnection) {
        inventoryDatabaseManager = new InventoryDatabaseManager(dbConnection);
        userDatabaseManager = new UserDatabaseManager(dbConnection);
        dashboardDatabaseManager = new DashboardCache(new DashboardDatabaseManager(dbConnection));
        preferencesDatabaseManager = new PreferencesDatabaseManager(dbConnection);
        checkoutDatabaseManager = new CheckoutDatabaseManager(dbConnection);
    }

    @Override
    public UserDAO getUserDAO() {
        return userDatabaseManager;
    }

    @Override
    public DashboardDAO getDashboardDAO() {
        return dashboardDatabaseManager;
    }

    @Override
    public PreferenceDAO getPreferenceDAO() {
        return preferencesDatabaseManager;
    }

    @Override
    public InventoryDAO getInventoryDAO() {
        return inventoryDatabaseManager;
    }

    @Override
    public CheckoutDAO getCheckoutDAO() {
        return checkoutDatabaseManager;
    }
}
