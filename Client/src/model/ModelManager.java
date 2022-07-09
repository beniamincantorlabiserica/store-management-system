package model;

import logger.Logger;
import logger.LoggerType;
import mediator.NetworkManager;
import networking.RemoteModel;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class ModelManager implements Model {
    private final RemoteModel clientModel;

    private User currentUser;

    private WorkingHours workingHours;

    public ModelManager() throws RemoteException {
        clientModel = new NetworkManager();
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
        // TODO @iustin
        // TODO Implement working hours.
        return null;
    }

    @Override
    public int getCheckoutsToday() {
        // TODO @beniamin
        return 0;
    }

    @Override
    public int getItemsToday() {
        // TODO @beniamin
        return 0;
    }

    @Override
    public int getSalesToday() {
        // TODO @beniamin
        return 0;
    }

    @Override
    public int getCheckoutsThisMonth() {
        // TODO @diana
        return 0;
    }

    @Override
    public int getItemsThisMonth() {
        // TODO @diana
        return 0;
    }

    @Override
    public int getSalesThisMonth() {
        // TODO @diana
        return 0;
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
}
