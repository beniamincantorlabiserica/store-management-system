package database;

public class ManagerFactory {
    private final GeneralDatabaseManager generalDatabaseManager;
    private final UsersDatabaseManager usersDatabaseManager;
    private final DashboardDatabaseManager dashboardDatabaseManager;

    public ManagerFactory(){
        generalDatabaseManager = new GeneralDatabaseManager();
        usersDatabaseManager = new UsersDatabaseManager();
        dashboardDatabaseManager = new DashboardDatabaseManager();
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
}
