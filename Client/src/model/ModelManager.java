package model;

import mediator.NetworkManager;
import networking.RemoteModel;

import java.rmi.RemoteException;

public class ModelManager implements Model {
    private final RemoteModel clientModel;

    public ModelManager() throws RemoteException {
        clientModel = new NetworkManager();
    }

    public boolean login(String password) {
        System.out.println("LocalModelManager here");
        try {
            return clientModel.login(password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
