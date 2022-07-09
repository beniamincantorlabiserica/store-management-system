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
    private final RemoteModel remoteModel;

    public NetworkManager() throws RuntimeException {
        try {
            remoteModel = (RemoteModel) Naming.lookup("rmi://localhost:1099/Shop");
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.ERROR,"RMI connection error (Stub lookup)");
            throw new RuntimeException("STUB_UNREACHABLE");
        }
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR,"RMI connection error (exportObject)");
        }
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
        try {
            return remoteModel.getWorkingHours();
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public void setOpeningHours(String openingTime) {
        try {
            remoteModel.setOpeningHours(openingTime);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setClosingHours(String closingTime) {
        try {
            remoteModel.setClosingHours(closingTime);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
