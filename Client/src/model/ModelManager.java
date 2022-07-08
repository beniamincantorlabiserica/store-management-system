package model;

import logger.Logger;
import logger.LoggerType;
import mediator.NetworkManager;
import networking.RemoteModel;

import java.rmi.RemoteException;

public class ModelManager implements Model {
    private final RemoteModel clientModel;

    public ModelManager() throws RemoteException {
        clientModel = new NetworkManager();
    }

    public User login(String password) {
        Logger.getInstance().log(LoggerType.DEBUG, "ModelManager -> login()");
        try {
            return clientModel.login(password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
