package viewmodel;

import model.ILocalModel;

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