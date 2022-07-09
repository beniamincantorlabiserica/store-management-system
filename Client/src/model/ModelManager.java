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
            workingHours = clientModel.getWorkingHours();
            return currentUser;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
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
}
