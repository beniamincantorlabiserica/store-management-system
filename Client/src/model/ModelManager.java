package model;

import logger.Logger;
import logger.LoggerType;
import mediator.NetworkManager;
import networking.RemoteModel;

import java.rmi.RemoteException;
import java.time.LocalTime;

public class ModelManager implements Model {
    private RemoteModel clientModel;

    private boolean network;
    private User currentUser;

    public ModelManager() {
        clientModel = null;
        network = false;
        try {
            clientModel = new NetworkManager();
            network = true;
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.ERROR, "Connection error. Is the server offline?");
        }
        currentUser = null;
    }

    public User login(String password) {
        Logger.getInstance().log(LoggerType.DEBUG, "ModelManager -> login()");
        if(isLoggedIn()) {
            Logger.getInstance().log(LoggerType.WARNING, "Already logged in. Log out first?");
            throw new RuntimeException("USER_ALREADY_LOGGED_IN");
        }
        try {
            currentUser = clientModel.login(password);
            return currentUser;
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Get working hours error");
            throw new RuntimeException("TODO");
        }
    }

    public void retryConnection() {
        try {
            clientModel = new NetworkManager();
            network = true;
        } catch (RuntimeException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Connection to the server failed again.");
        }
    }

    public boolean isNetwork() {
        return network;
    }

    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    @Override
    public void logout() {
        this.currentUser = null;
        Logger.getInstance().log("User logged out");
    }

    @Override
    public String getStoreStatus() {
        return LocalTime.now().getHour() < getClosingHourInteger() && LocalTime.now().getHour() > getOpeningHourInteger() ? "OPEN" : "CLOSED";
    }

    @Override
    public String getCheckoutsToday() {
        // TODO @beniamin
        return "TODO @beniamin";
    }

    @Override
    public String getItemsToday() {
        // TODO @beniamin
        return "TODO @beniamin";
    }

    @Override
    public String getSalesToday() {
        // TODO @beniamin
        return "TODO @beniamin";
    }

    @Override
    public String getCheckoutsThisMonth() {
        try {
            return clientModel.getCheckoutsThisMonth();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getItemsThisMonth() {
        try {
            return clientModel.getItemsThisMonth();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSalesThisMonth() {
        try {
            return clientModel.getSalesThisMonth();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setOpeningHours(String openingTime) {
        try {
            clientModel.setOpeningHours(openingTime);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setClosingHours(String closingTime) {
        try {
            clientModel.setClosingHours(closingTime);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getClosingHours() {
        try {
            return clientModel.getWorkingHours().getClosingTime();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getOpeningHours() {
        try {
            return clientModel.getWorkingHours().getOpeningTime();
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getClosingHourInteger() {
        return Integer.parseInt(getClosingHours().substring(0, 2));
    }

    @Override
    public int getOpeningHourInteger() {
        return Integer.parseInt(getOpeningHours().substring(0, 2));
    }
}
