package Database;

public class ManagerFactory {
    private final UsersDatabaseManager usersDatabaseManager;

    public ManagerFactory(){
        usersDatabaseManager = new UsersDatabaseManager();
    }

    public UsersDatabaseManager getUsersDatabaseManager() {
        return usersDatabaseManager;
    }
}
