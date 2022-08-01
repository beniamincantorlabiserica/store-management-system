package mediator;

import model.*;
import networking.RemoteModel;
import util.logger.Logger;
import util.logger.LoggerType;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerNetworkManager implements RemoteModel {
    private final ServerModel serverModel;

    public ServerNetworkManager(ServerModel serverModel, int serverPort) throws RemoteException, MalformedURLException {
        this.serverModel = serverModel;
        initRMI(serverPort);
    }

    /**
     * starts the Registry and the RMI Server
     *
     * @throws RemoteException       if registry could not start / a server is already running on the same port
     * @throws MalformedURLException if the server could not be started
     */
    private void initRMI(int serverPort) throws RemoteException, MalformedURLException {
        startRegistry(serverPort);
        startServer();
    }

    /**
     * starts the Registry
     *
     * @throws RemoteException if a server is already running on the specified port
     */
    private void startRegistry(int serverPort) throws RemoteException {
        try {
            LocateRegistry.createRegistry(serverPort);
            Logger.getInstance().log("RMI registry started");
        } catch (ExportException e) {
            Logger.getInstance().log(LoggerType.ERROR, "RMI registry already started on port " + serverPort + "? " + e.getMessage());
            throw new RemoteException("SERVER_ALREADY_RUNNING");
        }
    }

    /**
     * starts the RMI Server
     *
     * @throws RemoteException       if there was a connection issue
     * @throws MalformedURLException if the name contains illegal characters
     */
    private void startServer() throws RemoteException, MalformedURLException {
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("Shop", this);
        Logger.getInstance().log("RMI server started successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User login(String password) throws RemoteException {
        Logger.getInstance().log(LoggerType.DEBUG, "ServerModelManager -> login()");
        return serverModel.login(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePassword(String password, String role) {
        Logger.getInstance().log(LoggerType.DEBUG, "ServerModelManager -> updatePassword()");
        serverModel.updatePassword(password, role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMasterPassword() {
        return serverModel.getMasterPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkingHours getWorkingHours() {
        return serverModel.getWorkingHours();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpeningHours(String openingTime) {
        serverModel.setOpeningHours(openingTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClosingHours(String closingTime) {
        serverModel.setClosingHours(closingTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCheckoutsToday() throws RemoteException {
        return serverModel.getCheckoutsToday();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemsToday() throws RemoteException {
        return serverModel.getItemsToday();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSalesToday() throws RemoteException {
        return serverModel.getSalesToday();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCheckoutsThisMonth() throws RemoteException {
        return serverModel.getCheckoutsThisMonth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemsThisMonth() throws RemoteException {
        return serverModel.getItemsThisMonth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSalesThisMonth() throws RemoteException {
        return serverModel.getSalesThisMonth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getLockedState() throws RemoteException {
        return serverModel.getLockedState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLockedState(boolean b) throws RemoteException {
        serverModel.setLockedState(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Item> getItems() throws RemoteException {
        return serverModel.getItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePrice(Long id, Double price) {
        serverModel.changePrice(id, price);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQuantity(int id, int quantity) throws RemoteException {
        serverModel.updateQuantity(id, quantity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item scanItem(String barCode) throws RemoteException {
        return serverModel.scanItem(barCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double checkout() throws RemoteException {
        return serverModel.checkout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelCheckout() {
        serverModel.cancelCheckout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelCheckout(Integer checkoutId) {
        serverModel.cancelCheckout(checkoutId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void completePayment(PaymentType paymentType) {
        serverModel.completePayment(paymentType);
    }
}
