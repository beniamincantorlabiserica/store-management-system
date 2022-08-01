package model;

import database.DAOManager;
import util.logger.Logger;
import util.logger.LoggerType;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerModelManager implements ServerModel {
    private final DAOManager daoManager;

    private Integer checkoutId;

    public ServerModelManager(DAOManager daoManager) {
        this.daoManager = daoManager;
        Logger.getInstance().log("Starting server model..");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User login(String password) {
        return daoManager.getUserDAO().login(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePassword(String password, String role) {
        daoManager.getUserDAO().updatePassword(password, role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMasterPassword() {
        return daoManager.getUserDAO().getMasterPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkingHours getWorkingHours() {
        Logger.getInstance().log(LoggerType.DEBUG, "getWorkingHours() ServerModelManager");
        return daoManager.getDashboardDAO().getWorkingHours();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpeningHours(String openingTime) {
        daoManager.getDashboardDAO().setOpeningTime(openingTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClosingHours(String closingTime) {
        daoManager.getDashboardDAO().setClosingTime(closingTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCheckoutsToday() {
        try {
            return daoManager.getDashboardDAO().getCheckoutsToday();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch checkouts for today from server.");
        }
        return "Err";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemsToday() {
        try {
            return daoManager.getDashboardDAO().getItemsToday();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch items for today from server.");
        }
        return "Err";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSalesToday() {
        try {
            return daoManager.getDashboardDAO().getSalesToday();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch sales for today from server.");
        }
        return "Err";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCheckoutsThisMonth() {
        try {
            return daoManager.getDashboardDAO().getCheckoutsThisMonth();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch checkouts this month from server.");
        }
        return "Err";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemsThisMonth() {
        try {
            return daoManager.getDashboardDAO().getItemsThisMonth();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch items this month from server.");
        }
        return "Err";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSalesThisMonth() {
        try {
            return daoManager.getDashboardDAO().getSalesThisMonth();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch sales this month from server.");
        }
        return "Err";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getLockedState() {
        return daoManager.getPreferenceDAO().getLockedState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLockedState(boolean b) throws RuntimeException {
        daoManager.getPreferenceDAO().setLockedState(b);
        Logger.getInstance().log(LoggerType.DEBUG, "setLockedState(" + b + ")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void softRestart() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Item> getItems() {
        return daoManager.getInventoryDAO().getItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePrice(Long id, Double price) {
        daoManager.getInventoryDAO().changePrice(id, price);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQuantity(int id, int quantity) {
        daoManager.getInventoryDAO().updateQuantity(id, quantity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item scanItem(String barCode) throws RemoteException {
        int itemId = Integer.parseInt(barCode);
        Item addedItem = daoManager.getInventoryDAO().isItem(itemId);
        if (addedItem == null) {
            throw new RemoteException("WRONG_BARCODE");
        } else if (addedItem.getQuantity() == 0) {
            throw new RemoteException("NO_MORE_ITEMS_IN_STOCK");
        }
        if (this.checkoutId == null) {
            this.checkoutId = daoManager.getCheckoutDAO().getNextAvailableCheckoutNumber();
        }
        daoManager.getCheckoutDAO().addItemToCheckout(checkoutId, itemId, "UNPAID");
        daoManager.getInventoryDAO().updateQuantity(itemId, addedItem.getQuantity() - 1);
        addedItem.setQuantity(addedItem.getQuantity() - 1);
        return addedItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double checkout() throws RemoteException {
        if (this.checkoutId == null) {
            throw new RemoteException("NO_ITEMS_TO_CHECKOUT");
        }
        return daoManager.getCheckoutDAO().getTotalForCheckout(this.checkoutId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelCheckout() {
        daoManager.getCheckoutDAO().rollbackCheckout(this.checkoutId);
        this.checkoutId = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelCheckout(Integer checkoutId) {
        daoManager.getCheckoutDAO().rollbackCheckout(checkoutId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void completePayment(PaymentType paymentType) {
        daoManager.getCheckoutDAO().setPaymentType(this.checkoutId, paymentType);
        this.checkoutId = null;
    }
}
