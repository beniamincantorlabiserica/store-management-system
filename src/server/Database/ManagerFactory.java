package server.Database;

public class ManagerFactory {
    private UsersDatabaseManager usersDatabaseManager;

    public ManagerFactory(){

        usersDatabaseManager = new UsersDatabaseManager();
    }

    public UsersDatabaseManager getUsersDatabaseManager() {
        return usersDatabaseManager;
    }
}
