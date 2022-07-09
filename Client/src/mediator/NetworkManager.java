package mediator;

import logger.Logger;
import logger.LoggerType;
import model.User;
import model.WorkingHours;
import networking.RemoteModel;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NetworkManager implements RemoteModel {
    private RemoteModel remoteModel;

    public NetworkManager() throws RemoteException {
        try {
            remoteModel = (RemoteModel) Naming.lookup("rmi://localhost:1099/Shop");
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.ERROR,"RMI connection error");
            e.printStackTrace();
        }
        UnicastRemoteObject.exportObject(this, 0);
        Logger.getInstance().log("RMI connected successfully");
    }

    @Override
    public User login(String password) throws RemoteException{
        Logger.getInstance().log(LoggerType.DEBUG,"Login reached");
        return remoteModel.login(password);
    }

    @Override
    public void changePassword(String password, String role) throws RemoteException {
        Logger.getInstance().log(LoggerType.DEBUG,"Change password reached");
        remoteModel.changePassword(password, role);
    }

    @Override
    public WorkingHours getWorkingHours() {
        return null;
    }

    @Override
    public void setOpeningHours(String openingTime) {

    }

    @Override
    public void setClosingHours(String closingTime) {

    }
}
