package model;

import database.ManagerFactory;
import logger.Logger;
import logger.LoggerType;

import java.rmi.RemoteException;

public class ServerModelManager implements ServerModel {
    private final ManagerFactory managerFactory;

    private final WorkingHours workingHours;

    public ServerModelManager() {
        managerFactory = new ManagerFactory();
        managerFactory.getGeneralDatabaseManager().checkDB();
        workingHours = getWorkingHours();
    }

    @Override
    public User login(String password) {
        return managerFactory.getUsersDatabaseManager().login(password);
    }

    @Override
    public void updatePassword(String password, String role) {
        managerFactory.getUsersDatabaseManager().updatePassword(password, role);
    }

    @Override
    public WorkingHours getWorkingHours() {
        Logger.getInstance().log(LoggerType.DEBUG, "getWorkingHours() ServerModelManager");
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

    @Override
    public String getCheckoutsToday() {
        try {
            return managerFactory.getDashboardDatabaseManager().getCheckoutsToday();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch checkouts for today from server.");
        }
        return "Err";
    }

    @Override
    public String getItemsToday() {
        try {
            return managerFactory.getDashboardDatabaseManager().getItemsToday();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch items for today from server.");
        }
        return "Err";
    }

    @Override
    public String getSalesToday() {
        try {
            return managerFactory.getDashboardDatabaseManager().getSalesToday();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch sales for today from server.");
        }
        return "Err";    }

    @Override
    public String getCheckoutsThisMonth() {
        try {
            return managerFactory.getDashboardDatabaseManager().getCheckoutsThisMonth();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch checkouts this month from server.");
        }
        return "Err";
    }

    @Override
    public String getItemsThisMonth() {
        try {
            return managerFactory.getDashboardDatabaseManager().getItemsThisMonth();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch items this month from server.");
        }
        return "Err";
    }

    @Override
    public String getSalesThisMonth() {
        try {
            return managerFactory.getDashboardDatabaseManager().getSalesThisMonth();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch sales this month from server.");
        }
        return "Err";
    }

    private void updateWorkingHours() {
        managerFactory.getDashboardDatabaseManager().setWorkingHours(workingHours.getSQLReadyWorkingHours());
    }
}
