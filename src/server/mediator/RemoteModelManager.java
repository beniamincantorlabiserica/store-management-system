package server.mediator;

import server.model.ServerModel;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoteModelManager implements RemoteModel {
    private ServerModel serverModel;

    public RemoteModelManager(ServerModel serverModel) throws RemoteException, MalformedURLException {
        this.serverModel = serverModel;
        startRegistry();
        startServer();

    }

    @Override
    public boolean login(String password)throws RemoteException {
        System.out.println("I am in Remote class as well :O:O");
        return serverModel.login(password);
    }


    private void startServer() throws RemoteException, MalformedURLException {
        UnicastRemoteObject.exportObject( this, 0);
        Naming.rebind("Shop",  this);
        System.out.println("Server started...");
    }
    private void startRegistry() throws RemoteException {
        try {
           Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        }
        catch (ExportException e) {
            System.out.println("Registry already started - " + e.getMessage());
        }
    }
}
