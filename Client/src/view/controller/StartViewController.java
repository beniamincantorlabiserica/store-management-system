package view.controller;

import javafx.scene.control.PasswordField;
import logger.Logger;
import logger.LoggerType;
import model.User;
import view.View;
import view.ViewController;
import viewmodel.StartViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class StartViewController extends ViewController {
    private StartViewModel viewModel;
    @FXML
    private PasswordField passwordField;

    public StartViewController(){

    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getStartViewModel();
    }

    @Override
    public void reset() {
        passwordField.clear();
        viewModel.reset();
    }

    public void login(ActionEvent actionEvent) {
        User user;
        try {
            user = viewModel.login(passwordField.getText());
            if (user != null) {
                if (user.isMaster()) {
                    getViewHandler().openView(View.DASHBOARD);
                    Logger.getInstance().log("Master logged in!");
                } else {
                    // TODO cashier UI and use of it
                    Logger.getInstance().log("Cashier logged in!");
                }
            } else {
                Logger.getInstance().log(LoggerType.WARNING, "Wrong password");
            }
        } catch (RuntimeException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Already logged in");
        } finally {
            reset();
        }
    }
}
