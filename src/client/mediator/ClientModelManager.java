package client.mediator;

import server.mediator.RemoteModel;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientModelManager implements ClientModel, Remote {
    private RemoteModel remoteModel;

    public ClientModelManager() throws RemoteException, MalformedURLException, NotBoundException {
        try{
        remoteModel = (RemoteModel) Naming.lookup("rmi://localhost:1099/Shop");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        UnicastRemoteObject.exportObject(this, 0);
    }



    @Override
    public boolean login(String password){
        System.out.println("ClientModelManager here :)");
        try {
            return remoteModel.login(password);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }


}
