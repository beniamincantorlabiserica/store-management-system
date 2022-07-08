package view.controller;

import logger.Logger;
import logger.LoggerType;
import view.ViewController;
import viewmodel.StartViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class StartViewController extends ViewController {
    private StartViewModel viewModel;
    @FXML
    private TextField passwordTextField;

    public StartViewController(){

    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getStartViewModel();
    }

    @Override
    public void reset() {
        viewModel.reset();
    }
    public void login(ActionEvent actionEvent) {
        if (viewModel.login(passwordTextField.getText()) == null) {
            Logger.getInstance().log(LoggerType.WARNING, "Wrong password");
        } else if (viewModel.login(passwordTextField.getText()).isMaster()){
            Logger.getInstance().log("Master logged in!");
        } else {
            Logger.getInstance().log("Cashier logged in!");
        }
    }
}
