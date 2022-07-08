package model;

import mediator.ClientModel;
import mediator.ClientModelManager;
import model.User;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LocalModelManager implements ILocalModel {
    private ClientModel clientModel;
    public LocalModelManager() throws MalformedURLException, NotBoundException, RemoteException {
     clientModel = new ClientModelManager();
    }



    @Override
    public boolean login(String password){
        System.out.println("LocalModelManager here");
        return clientModel.login(password);
    }


}
