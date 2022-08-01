package model;

import logger.Logger;
import logger.LoggerType;
import mediator.NetworkManager;
import networking.RemoteModel;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;

/**
 * The client model manager
 */
public class ModelManager implements Model {
    private RemoteModel clientModel;
    /**
     * @value true if the client successfully connected to the server through RMI
     * false otherwise
     */
    private boolean network;
    private String locked;
    private User currentUser;

    private ArrayList<Item> currentCheckout;

    /**
     * no-args constructor
     */
    public ModelManager() {
        clientModel = null;
        network = false;
        locked = null;
        try {
            clientModel = new NetworkManager();
            network = true;
        } catch (Exception e) {
            Logger.getInstance().log(LoggerType.ERROR, "Connection error. Is the server offline?");
        }
        currentUser = null;
        currentCheckout = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User login(String password) {
        Logger.getInstance().log(LoggerType.DEBUG, "ModelManager -> login()");
        if (isLoggedIn()) {
            Logger.getInstance().log(LoggerType.WARNING, "Already logged in. Log out first?");
            throw new RuntimeException("USER_ALREADY_LOGGED_IN");
        }
        try {
            currentUser = clientModel.login(password);
            return currentUser;
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Get working hours error");
            throw new RuntimeException("TODO");
        }
    }

    /**
     * {@inheritDoc}
     * mark the network as available if the client can connect to the server
     * log an error otherwise
     */
    @Override
    public void retryConnection() {
        try {
            clientModel = new NetworkManager();
            network = true;
        } catch (RuntimeException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Connection to the server failed again.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return value of the variable network
     */
    @Override
    public boolean isNetwork() {
        return network;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean masterCheck(String s) {
        if (currentUser == null) {
            try {
                return s.equals(clientModel.getMasterPassword());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return s.equals(currentUser.getMasterPassword());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePassword(String role, String password) {
        try {
            clientModel.updatePassword(role, password);
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.DEBUG, "Remote exception in updating password: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser() {
        return currentUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout() {
        this.currentUser = null;
        this.locked = null;
        Logger.getInstance().log("User logged out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStoreStatus() {
        return LocalTime.now().getHour() < getClosingHourInteger() && LocalTime.now().getHour() > getOpeningHourInteger() ? "OPEN" : "CLOSED";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCheckoutsToday() {
        try {
            return clientModel.getCheckoutsToday();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemsToday() {
        try {
            return clientModel.getItemsToday();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSalesToday() {
        try {
            return clientModel.getSalesToday();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCheckoutsThisMonth() {
        try {
            return clientModel.getCheckoutsThisMonth();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemsThisMonth() {
        try {
            return clientModel.getItemsThisMonth();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSalesThisMonth() {
        try {
            return clientModel.getSalesThisMonth();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * sets the locked state to the appropriate one given the working hours and current time
     */
    private void checkLockedState() {
        setLockedState(!isOpen());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClosingHours() {
        try {
            return clientModel.getWorkingHours().getClosingTime();
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
            clientModel.setClosingHours(closingTime);
            checkLockedState();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpeningHours() {
        try {
            return clientModel.getWorkingHours().getOpeningTime();
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpeningHours(String openingTime) {
        try {
            clientModel.setOpeningHours(openingTime);
            checkLockedState();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getClosingHourInteger() {
        return Integer.parseInt(getClosingHours().substring(0, 2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOpeningHourInteger() {
        return Integer.parseInt(getOpeningHours().substring(0, 2));
    }

    @Override
    public double getDayProgress() {
        if (!isOpen()) {
            return 0;
        }
        return (double) (LocalTime.now().getHour() - getOpeningHourInteger()) /
                (double) (getClosingHourInteger() - getOpeningHourInteger());
    }

    @Override
    public double getMonthProgress() {
        return (double) LocalDateTime.now().getDayOfMonth() / (double) LocalDateTime.now().getMonth().length(Year.isLeap(LocalDateTime.now().getYear()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOpen() {
        LocalTime timeNow = LocalTime.now();
        LocalTime openingTime = LocalTime.parse(getOpeningHours());
        LocalTime closingTime = LocalTime.parse(getClosingHours());
        return !timeNow.isAfter(closingTime) && !timeNow.isBefore(openingTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getLockedState() throws RemoteException {
        locked = String.valueOf(clientModel.getLockedState());
        return Boolean.parseBoolean(locked);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLockedState(boolean b) {
        if (locked.equals(String.valueOf(b))) {
            Logger.getInstance().log(LoggerType.ERROR, "Trying to overwrite server data with the same data, cancelling..");
            return;
        }
        try {
            clientModel.setLockedState(b);
            locked = String.valueOf(b);
        } catch (Exception e) {
            throw new RuntimeException("LOCK_STATE_SET_FAILED");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Item> getItems() {
        try {
            return clientModel.getItems();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePrice(Long id, Double price) {
        try {
            clientModel.changePrice(id, price);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQuantity(int id, int quantity) {
        try {
            clientModel.updateQuantity(id, quantity);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Item> scanItem(String barCode) throws RuntimeException {
        Item addedItem;
        try {
            addedItem = clientModel.scanItem(barCode);
            addedItem.setQuantity(1);
            currentCheckout.add(addedItem);
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, "scanItem ModelManager error: " + e.getMessage());
            if (e.getMessage().contains("NO_MORE_ITEMS_IN_STOCK")) {
                throw new RuntimeException("NO_MORE_ITEMS_IN_STOCK");
            } else {
                throw new RuntimeException("WRONG_BARCODE");
            }
        }
        return currentCheckout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double checkout() throws RuntimeException {
        currentCheckout = new ArrayList<>();
        try {
            return clientModel.checkout();
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void completePayment(PaymentType paymentType) throws RuntimeException {
        try {
            clientModel.completePayment(paymentType);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelCheckout() throws RuntimeException {
        try {
            clientModel.cancelCheckout();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undoCheckout(Integer checkoutId) throws RuntimeException {
        try {
            clientModel.cancelCheckout(checkoutId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
