package model;

import mediator.ClientModelManager;
import model.User;
import networking.RemoteModel;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LocalModelManager implements ILocalModel {
    private RemoteModel clientModel;
    public LocalModelManager() throws MalformedURLException, NotBoundException, RemoteException {
        clientModel = new ClientModelManager();
    }



    @Override
    public boolean login(String password) throws RemoteException {
        System.out.println("LocalModelManager here");
        return clientModel.login(password);
    }


}
