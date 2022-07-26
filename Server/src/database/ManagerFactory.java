package database;

import logger.Logger;
import logger.LoggerType;

import java.sql.SQLException;

public class ManagerFactory {
    private final DBConnection dbConnection;
    private final GeneralDatabaseManager generalDatabaseManager;
    private final UsersDatabaseManager usersDatabaseManager;
    private final DashboardDatabaseManager dashboardDatabaseManager;
    private final PreferencesDatabaseManager preferencesDatabaseManager;
    private final InventoryDatabaseManager inventoryDatabaseManager;
    private final CheckoutDatabaseManager checkoutDatabaseManager;

    public ManagerFactory() {
        this.dbConnection = new DBConnection();
        inventoryDatabaseManager = new InventoryDatabaseManager(dbConnection);
        generalDatabaseManager = new GeneralDatabaseManager(dbConnection);
        usersDatabaseManager = new UsersDatabaseManager(dbConnection);
        dashboardDatabaseManager = new DashboardDatabaseManager(dbConnection);
        preferencesDatabaseManager = new PreferencesDatabaseManager(dbConnection);
        checkoutDatabaseManager = new CheckoutDatabaseManager(dbConnection);
    }

    public GeneralDatabaseManager getGeneralDatabaseManager() {
        return generalDatabaseManager;
    }

    public UsersDatabaseManager getUsersDatabaseManager() {
        return usersDatabaseManager;
    }

    public DashboardDatabaseManager getDashboardDatabaseManager() {
        return dashboardDatabaseManager;
    }

    public PreferencesDatabaseManager getPreferenceDatabaseManager() {
        return preferencesDatabaseManager;
    }

    public InventoryDatabaseManager getInventoryDatabaseManager() {
        return inventoryDatabaseManager;
    }

    public CheckoutDatabaseManager getCheckoutDatabaseManager() {
        return checkoutDatabaseManager;
    }

    public void closeConnection() {
        try {
            dbConnection.close();
            Logger.getInstance().log("Database connection closed successfully");
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Database connection failed to close!");
            e.printStackTrace();
        }
    }
}
