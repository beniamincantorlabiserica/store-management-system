package model;

import database.ManagerFactory;
import logger.Logger;
import logger.LoggerType;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerModelManager implements ServerModel {
    private ManagerFactory managerFactory;

    private WorkingHours workingHours;

    private boolean isInventoryCacheValid;

    private ArrayList<Item> inventoryCache;

    private Integer checkoutId;

    public ServerModelManager() {
        this.isInventoryCacheValid = false;
        Logger.getInstance().log("Starting server model..");
        boot();
    }

    private void boot() {
        this.managerFactory = new ManagerFactory();
        this.managerFactory.getGeneralDatabaseManager().checkDB();
        this.workingHours = getWorkingHours();
        this.updateInventoryCache();
        this.checkoutId = null;
    }

    @Override
    public User login(String password) {
        return managerFactory.getUsersDatabaseManager().login(password);
    }

    @Override
    public void updatePassword(String password, String role) {
        managerFactory.getUsersDatabaseManager().updatePassword(password, role);
    }

    @Override
    public String getMasterPassword() {
        return managerFactory.getUsersDatabaseManager().getMasterPassword();
    }

    @Override
    public WorkingHours getWorkingHours() {
        Logger.getInstance().log(LoggerType.DEBUG, "getWorkingHours() ServerModelManager");
        return managerFactory.getDashboardDatabaseManager().getWorkingHours();
    }

    @Override
    public void setOpeningHours(String openingTime) {
        workingHours.setOpeningTime(openingTime);
        updateWorkingHours();
    }

    @Override
    public void setClosingHours(String closingTime) {
        workingHours.setClosingTime(closingTime);
        updateWorkingHours();
    }

    @Override
    public String getCheckoutsToday() {
        try {
            return managerFactory.getDashboardDatabaseManager().getCheckoutsToday();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch checkouts for today from server.");
        }
        return "Err";
    }

    @Override
    public String getItemsToday() {
        try {
            return managerFactory.getDashboardDatabaseManager().getItemsToday();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch items for today from server.");
        }
        return "Err";
    }

    @Override
    public String getSalesToday() {
        try {
            return managerFactory.getDashboardDatabaseManager().getSalesToday();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch sales for today from server.");
        }
        return "Err";    }

    @Override
    public String getCheckoutsThisMonth() {
        try {
            return managerFactory.getDashboardDatabaseManager().getCheckoutsThisMonth();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch checkouts this month from server.");
        }
        return "Err";
    }

    @Override
    public String getItemsThisMonth() {
        try {
            return managerFactory.getDashboardDatabaseManager().getItemsThisMonth();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch items this month from server.");
        }
        return "Err";
    }

    @Override
    public String getSalesThisMonth() {
        try {
            return managerFactory.getDashboardDatabaseManager().getSalesThisMonth();
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.WARNING, "Could not fetch sales this month from server.");
        }
        return "Err";
    }

    private void updateWorkingHours() {
        managerFactory.getDashboardDatabaseManager().setWorkingHours(workingHours.getSQLReadyWorkingHours());
    }

    @Override
    public void setLockedState(boolean b) throws RuntimeException {
        managerFactory.getPreferenceDatabaseManager().setLockedState(b);
        Logger.getInstance().log(LoggerType.DEBUG, "setLockedState(" + b + ")");
    }

    @Override
    public boolean getLockedState() {
        return managerFactory.getPreferenceDatabaseManager().getLockedState();
    }

    public void powerOff() {
        managerFactory.closeConnection();
    }

    @Override
    public void softRestart() {
        powerOff();
        boot();
    }

    @Override
    public ArrayList<Item> getItems() {
        if (!isInventoryCacheValid) {
            updateInventoryCache();
        }
        return inventoryCache;
    }

    private void updateInventoryCache() {
        this.inventoryCache = managerFactory.getInventoryDatabaseManager().getItems();
        this.isInventoryCacheValid = true;
    }

    @Override
    public void changePrice(Long id, Double price) {
        managerFactory.getInventoryDatabaseManager().changePrice(id, price);
        isInventoryCacheValid = false;
    }

    @Override
    public Item scanItem(String barCode) throws RemoteException {
        int itemId = Integer.parseInt(barCode);
        Item addedItem = managerFactory.getInventoryDatabaseManager().isItem(itemId);
        if (addedItem == null) {
            throw new RemoteException("WRONG_BARCODE");
        } else if (addedItem.getQuantity() == 0) {
            throw new RemoteException("NO_MORE_ITEMS_IN_STOCK");
        }
        if (this.checkoutId == null) {
            this.checkoutId = managerFactory.getCheckoutDatabaseManager().getNextAvailableCheckoutNumber();
        }
        managerFactory.getCheckoutDatabaseManager().addItemToCheckout(checkoutId, itemId, "UNPAID");
        managerFactory.getInventoryDatabaseManager().updateQuantity(itemId, addedItem.getQuantity() - 1);
        isInventoryCacheValid = false;
        addedItem.setQuantity(addedItem.getQuantity() - 1);
        return addedItem;
    }

    @Override
    public Double checkout() throws RemoteException {
        if (this.checkoutId == null) {
            throw new RemoteException("NO_ITEMS_TO_CHECKOUT");
        }
        return managerFactory.getCheckoutDatabaseManager().getTotalForCheckout(this.checkoutId);
    }

    @Override
    public void cancelCheckout() {
        managerFactory.getCheckoutDatabaseManager().rollbackCheckout(this.checkoutId);
        isInventoryCacheValid = false;
        this.checkoutId = null;
    }

    @Override
    public void cancelCheckout(Integer checkoutId) {
        managerFactory.getCheckoutDatabaseManager().rollbackCheckout(checkoutId);
        isInventoryCacheValid = false;
    }

    @Override
    public void completePayment(PaymentType paymentType) {
        managerFactory.getCheckoutDatabaseManager().setPaymentType(this.checkoutId, paymentType);
        isInventoryCacheValid = false;
        this.checkoutId = null;
    }
}
