package mediator;

import logger.Logger;
import logger.LoggerType;
import model.ServerModel;
import model.User;
import networking.RemoteModel;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

public class ServerModelManager implements RemoteModel {
    private final ServerModel serverModel;

    public ServerModelManager(ServerModel serverModel) throws RemoteException, MalformedURLException {
        this.serverModel = serverModel;
        startRegistry();
        startServer();

    }

    @Override
    public User login(String password)throws RemoteException {
        Logger.getInstance().log(LoggerType.DEBUG, "ServerModelManager -> login()");
        return serverModel.login(password);
    }

    private void startServer() throws RemoteException, MalformedURLException {
        UnicastRemoteObject.exportObject( this, 0);
        Naming.rebind("Shop",  this);
        Logger.getInstance().log("RMI server started");
    }
    private void startRegistry() throws RemoteException {
        try {
           Registry registry = LocateRegistry.createRegistry(1099);
            Logger.getInstance().log("RMI registry started");
        }
        catch (ExportException e) {
            Logger.getInstance().log(LoggerType.ERROR, "RMI registry already started? " + e.getMessage());
        }
    }
}
