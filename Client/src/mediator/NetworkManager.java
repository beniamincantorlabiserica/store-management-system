package mediator;

import logger.Logger;
import logger.LoggerType;
import model.Item;
import model.PaymentType;
import model.User;
import model.WorkingHours;
import networking.RemoteModel;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Provides the client side segment of the communication between the client and the server
 * Used by the ModelManager
 */
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

    /** passes the request to change the password for a specific role to the server
     * @param password the new password for the respective role
     * @param role the role to get the password changed
     * @throws RemoteException in case the password is the same as before / the password is
     *                         the same as the one from another role
     */
    @Override
    public void updatePassword(String password, String role) throws RemoteException {
        Logger.getInstance().log(LoggerType.DEBUG,"Change password reached");
        remoteModel.updatePassword(password, role);
    }

    @Override
    public String getMasterPassword() throws RemoteException {
        return remoteModel.getMasterPassword();
    }

    /** receives an object with information about the working hours from the server
     * @return WorkingHours object
     */
    @Override
    public WorkingHours getWorkingHours() {
        try {
            return remoteModel.getWorkingHours();
        } catch (RemoteException e) {
            return null;
        }
    }

    /** passes the request to set the opening time to the server
     * @param openingTime a string containing 5 characters, out of which the first
     *                    two are representing the hour and the last two the minutes
     */
    @Override
    public void setOpeningHours(String openingTime) {
        try {
            remoteModel.setOpeningHours(openingTime);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /** passes the request to set the closing time to the server
     * @param closingTime a string containing 5 characters, out of which the first
     *                    two are representing the hour and the last two the minutes
     */
    @Override
    public void setClosingHours(String closingTime) {
        try {
            remoteModel.setClosingHours(closingTime);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCheckoutsToday() throws RemoteException {
        return remoteModel.getCheckoutsToday();
    }

    @Override
    public String getItemsToday() throws RemoteException {
        return remoteModel.getItemsToday();
    }

    @Override
    public String getSalesToday() throws RemoteException {
        return remoteModel.getSalesToday();
    }

    @Override
    public String getCheckoutsThisMonth() throws RemoteException {
        return remoteModel.getCheckoutsThisMonth();
    }

    @Override
    public String getItemsThisMonth() throws RemoteException {
        return remoteModel.getItemsThisMonth();
    }

    @Override
    public String getSalesThisMonth() throws RemoteException {
        return remoteModel.getSalesThisMonth();
    }

    @Override
    public void setLockedState(boolean b) throws RemoteException {
        remoteModel.setLockedState(b);
    }

    @Override
    public boolean getLockedState() throws RemoteException {
        return remoteModel.getLockedState();
    }

    @Override
    public ArrayList<Item> getItems() throws RemoteException {
        return remoteModel.getItems();
    }

    @Override
    public void changePrice(Long id, Double price) throws RemoteException {
        remoteModel.changePrice(id, price);
    }

    @Override
    public Item scanItem(String barCode) throws RemoteException {
        return remoteModel.scanItem(barCode);
    }

    @Override
    public Double checkout() throws RemoteException {
        return remoteModel.checkout();
    }

    @Override
    public void completePayment(PaymentType paymentType) {
        remoteModel.completePayment(paymentType);
    }
}
