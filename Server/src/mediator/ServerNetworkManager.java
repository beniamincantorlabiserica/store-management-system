package mediator;

import logger.Logger;
import logger.LoggerType;
import model.ServerModel;
import model.User;
import model.WorkingHours;
import networking.RemoteModel;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

public class ServerNetworkManager implements RemoteModel {
    private final ServerModel serverModel;

    public ServerNetworkManager(ServerModel serverModel) throws RemoteException, MalformedURLException {
        this.serverModel = serverModel;
        startRegistry();
        startServer();
    }

    @Override
    public User login(String password) throws RemoteException {
        Logger.getInstance().log(LoggerType.DEBUG, "ServerModelManager -> login()");
        return serverModel.login(password);
    }

    @Override
    public void changePassword(String password, String role) throws RemoteException {
        Logger.getInstance().log(LoggerType.DEBUG, "ServerModelManager -> changePassword()");
        serverModel.changePassword(password, role);
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

    @Override
    public WorkingHours getWorkingHours() {
        return serverModel.getWorkingHours();
    }

    @Override
    public void setOpeningHours(String openingTime) {
        serverModel.setOpeningHours(openingTime);
    }

    @Override
    public void setClosingHours(String closingTime) {
        serverModel.setClosingHours(closingTime);
    }

    @Override
    public String getCheckoutsToday() throws RemoteException {
        return serverModel.getCheckoutsToday();
    }

    @Override
    public String getItemsToday() throws RemoteException {
        return serverModel.getItemsToday();
    }

    @Override
    public String getSalesToday() throws RemoteException {
        return serverModel.getSalesToday();
    }
    @Override
    public String getCheckoutsThisMonth() throws RemoteException {
        return serverModel.getCheckoutsThisMonth();
    }

    @Override
    public String getItemsThisMonth() throws RemoteException {
        return serverModel.getItemsThisMonth();
    }

    @Override
    public String getSalesThisMonth() throws RemoteException {
        return serverModel.getSalesThisMonth();
    }
}
