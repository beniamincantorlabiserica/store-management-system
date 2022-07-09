package Database;

public class ManagerFactory {
    private final UsersDatabaseManager usersDatabaseManager;
    private final DashboardDatabaseManager dashboardDatabaseManager;

    public ManagerFactory(){
        usersDatabaseManager = new UsersDatabaseManager();
        dashboardDatabaseManager = new DashboardDatabaseManager();
    }

    public UsersDatabaseManager getUsersDatabaseManager() {
        return usersDatabaseManager;
    }

    public DashboardDatabaseManager getDashboardDatabaseManager() {
        return dashboardDatabaseManager;
    }
}
