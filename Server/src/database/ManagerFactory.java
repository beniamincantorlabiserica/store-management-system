package database;

import logger.Logger;
import networking.RemoteSettingsModel;

import java.sql.Connection;
import java.sql.SQLException;

public class ManagerFactory {
    private final Connection connection;
    private final GeneralDatabaseManager generalDatabaseManager;
    private final UsersDatabaseManager usersDatabaseManager;
    private final DashboardDatabaseManager dashboardDatabaseManager;
    private final PreferencesDatabaseManager preferencesDatabaseManager;

    public ManagerFactory(){
        DBConnection dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        generalDatabaseManager = new GeneralDatabaseManager(connection);
        usersDatabaseManager = new UsersDatabaseManager(connection);
        dashboardDatabaseManager = new DashboardDatabaseManager(connection);
        preferencesDatabaseManager = new PreferencesDatabaseManager(connection);
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

    public void closeConnection() {
        try {
            connection.close();
            Logger.getInstance().log("Database connection closed successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
