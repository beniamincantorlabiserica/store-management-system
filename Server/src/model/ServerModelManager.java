package model;

import Database.ManagerFactory;

public class ServerModelManager implements ServerModel {
    private final ManagerFactory managerFactory;

    private WorkingHours workingHours;

    public ServerModelManager() {
        managerFactory = new ManagerFactory();
        managerFactory.getGeneralDatabaseManager().checkDB();
        workingHours = getWorkingHours();
    }

    public void changePassword(String password, String role) {
        managerFactory.getUsersDatabaseManager().updatePassword(password, role);
    }
    @Override
    public User login(String password) {
        return managerFactory.getUsersDatabaseManager().login(password);
    }

    @Override
    public WorkingHours getWorkingHours() {
        return managerFactory.getDashboardDatabaseManager().getWorkingHours();
    }

    @Override
    public void setOpeningHours(String openingTime) {
        workingHours.setOpeningTime(openingTime);
        updateWorkingHours();
    }

    @Override
    public void setClosingHours(String closingTime) {
        workingHours.setClosingTime(closingTime);
        updateWorkingHours();
    }

    private void updateWorkingHours() {
        managerFactory.getDashboardDatabaseManager().setWorkingHours(workingHours.getSQLReadyWorkingHours());
    }
}
