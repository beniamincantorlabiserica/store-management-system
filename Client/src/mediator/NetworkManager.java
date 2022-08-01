package mediator;

import model.Item;
import model.PaymentType;
import model.User;
import model.WorkingHours;
import networking.RemoteModel;
import util.logger.Logger;
import util.logger.LoggerType;

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

    private WorkingHours workingHoursCache;
    private String checkoutsTodayCache;
    private String checkoutsThisMonthCache;
    private String itemsTodayCache;
    private String itemsThisMonthCache;
    private String salesTodayCache;
    private String salesThisMonthCache;
    private ArrayList<Item> itemsCache;

    public NetworkManager() throws RuntimeException {
        try {
            remoteModel = (RemoteModel) Naming.lookup("rmi://localhost:1099/Shop");
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.ERROR, "RMI connection error (Stub lookup)");
            throw new RuntimeException("STUB_UNREACHABLE");
        }
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, "RMI connection error (exportObject)");
        }
        Logger.getInstance().log("RMI connected successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User login(String password) throws RemoteException {
        Logger.getInstance().log(LoggerType.DEBUG, "Login reached");
        return remoteModel.login(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePassword(String password, String role) throws RemoteException {
        Logger.getInstance().log(LoggerType.DEBUG, "Change password reached");
        remoteModel.updatePassword(password, role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMasterPassword() throws RemoteException {
        return remoteModel.getMasterPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkingHours getWorkingHours() {
        if (workingHoursCache == null) {
            try {
                workingHoursCache = remoteModel.getWorkingHours();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return workingHoursCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpeningHours(String openingTime) {
        try {
            remoteModel.setOpeningHours(openingTime);
            workingHoursCache = null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClosingHours(String closingTime) {
        try {
            remoteModel.setClosingHours(closingTime);
            workingHoursCache = null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCheckoutsToday() throws RemoteException {
        if (checkoutsTodayCache == null) {
            checkoutsTodayCache = remoteModel.getCheckoutsToday();
        }
        return checkoutsTodayCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemsToday() throws RemoteException {
        if (itemsTodayCache == null) {
            itemsTodayCache = remoteModel.getItemsToday();
        }
        return itemsTodayCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSalesToday() throws RemoteException {
        if (salesTodayCache == null) {
            salesTodayCache = remoteModel.getSalesToday();
        }
        return salesTodayCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCheckoutsThisMonth() throws RemoteException {
        if (checkoutsThisMonthCache == null) {
            checkoutsThisMonthCache = remoteModel.getCheckoutsThisMonth();
        }
        return checkoutsThisMonthCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemsThisMonth() throws RemoteException {
        if (itemsThisMonthCache == null) {
            itemsThisMonthCache = remoteModel.getItemsThisMonth();
        }
        return itemsThisMonthCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSalesThisMonth() throws RemoteException {
        if (salesThisMonthCache == null) {
            salesThisMonthCache = remoteModel.getSalesThisMonth();
        }
        return salesThisMonthCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getLockedState() throws RemoteException {
        return remoteModel.getLockedState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLockedState(boolean b) throws RemoteException {
        remoteModel.setLockedState(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Item> getItems() throws RemoteException {
        if (itemsCache == null) {
            itemsCache = remoteModel.getItems();
        }
        return itemsCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePrice(Long id, Double price) throws RemoteException {
        remoteModel.changePrice(id, price);
        itemsCache = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQuantity(int id, int quantity) throws RemoteException {
        remoteModel.updateQuantity(id, quantity);
        itemsCache = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item scanItem(String barCode) throws RemoteException {
        itemsCache = null;
        return remoteModel.scanItem(barCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double checkout() throws RemoteException {
        return remoteModel.checkout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelCheckout() throws RemoteException {
        itemsCache = null;
        remoteModel.cancelCheckout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelCheckout(Integer checkoutId) throws RemoteException {
        itemsCache = null;
        remoteModel.cancelCheckout(checkoutId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void completePayment(PaymentType paymentType) throws RemoteException {
        remoteModel.completePayment(paymentType);
    }
}
