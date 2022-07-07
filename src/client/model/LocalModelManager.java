package client.model;

import client.mediator.ClientModel;
import client.mediator.ClientModelManager;
import server.model.classes.User;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
