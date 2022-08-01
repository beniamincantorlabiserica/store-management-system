package viewmodel;

import logger.Logger;
import logger.LoggerType;
import model.Model;
import model.User;

import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;

public class StartViewModel implements StartViewModelInterface {
    private final Model model;

    public StartViewModel(Model model, ViewModelState viewModelState) {
        this.model = model;
    }

    @Override
    public void reset() {
        Logger.getInstance().log(LoggerType.DEBUG, "StartViewModel reset()");
    }

    @Override
    public boolean isNetwork() {
        return model.isNetwork();
    }

    @Override
    public void retryNetwork() {
        model.retryConnection();
    }

    @Override
    public void logout() {
        model.logout();
    }

    @Override
    public String getOpeningHours() {
        return model.getOpeningHours().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String getClosingHours() {
        return model.getClosingHours().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public boolean getLockedState() throws RemoteException {
        return model.getLockedState();
    }

    @Override
    public boolean masterCheck(String s) {
        return model.masterCheck(s);
    }

    @Override
    public void setLockedState(boolean b) {
        model.setLockedState(b);
    }

    /**
     * @return true if the store is open (between the opening and the closing time) or closed
     */
    @Override
    public boolean isOpen() {
        return model.isOpen();
    }

    @Override
    public User login(String password) {
        Logger.getInstance().log(LoggerType.DEBUG, "StartViewModel -> login()");
        try {
            return model.login(password);
        } catch (RuntimeException e) {
            Logger.getInstance().log(LoggerType.ERROR, "StartViewModel login() error");
            throw new RuntimeException(e);
        }
    }

}