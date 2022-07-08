package viewmodel;

import model.ILocalModel;

import java.rmi.RemoteException;

public class StartViewModel implements StartViewModelInterface {
    private ILocalModel localModel;

    public StartViewModel(ILocalModel model, ViewModelState viewModelState) {
        localModel = model;
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean login(String password){
        System.out.println("Start view model here :O");
        try {
            return localModel.login(password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}