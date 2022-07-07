package client.viewmodel;

import client.model.ILocalModel;
import client.view.controller.StartViewController;

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
        return localModel.login(password);
    }
}