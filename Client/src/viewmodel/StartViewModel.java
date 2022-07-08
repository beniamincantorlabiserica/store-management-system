package viewmodel;

import logger.Logger;
import logger.LoggerType;
import model.Model;
import model.User;

import java.rmi.RemoteException;

public class StartViewModel implements StartViewModelInterface {
    private final Model model;

    public StartViewModel(Model model, ViewModelState viewModelState) {
        this.model = model;
    }

    @Override
    public void reset() {

    }

    @Override
    public User login(String password){
        Logger.getInstance().log(LoggerType.DEBUG, "StartViewModel -> login()");
        try {
            return model.login(password);
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, "StartViewModel login() error");
            throw new RuntimeException(e);
        }
    }
}