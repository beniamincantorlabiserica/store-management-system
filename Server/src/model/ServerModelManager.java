package model;

import Database.ManagerFactory;

public class ServerModelManager implements ServerModel{
    private ManagerFactory managerFactory;

    public ServerModelManager() {
        managerFactory = new ManagerFactory();
    }



    @Override
    public boolean login(String password) {
        return managerFactory.getUsersDatabaseManager().login(password);
    }

}
