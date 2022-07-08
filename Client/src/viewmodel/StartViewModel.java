package viewmodel;

import model.Model;

import java.rmi.RemoteException;

public class StartViewModel implements StartViewModelInterface {
    private Model localModel;

    public StartViewModel(Model model, ViewModelState viewModelState) {
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