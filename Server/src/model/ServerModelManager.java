package model;

import Database.ManagerFactory;

public class ServerModelManager implements ServerModel{
    private final ManagerFactory managerFactory;

    public ServerModelManager() {
        managerFactory = new ManagerFactory();
    }

    public void changePassword(String password, String role) {
        managerFactory.getUsersDatabaseManager().updatePassword(password, role);
    }
    @Override
    public User login(String password) {
        return managerFactory.getUsersDatabaseManager().login(password);
    }
}
