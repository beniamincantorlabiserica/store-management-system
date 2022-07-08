package mediator;

import logger.Logger;
import logger.LoggerType;
import networking.RemoteModel;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientModelManager implements RemoteModel {
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
        Logger.getInstance().log(LoggerType.DEBUG,"Login reached");
        try {
            return remoteModel.login(password);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }


}
