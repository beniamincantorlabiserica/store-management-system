package view.controller;

import javafx.scene.control.Alert;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class StartViewController extends ViewController {
    private StartViewModel viewModel;
    @FXML
    private PasswordField passwordField;

    public StartViewController() {

    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getStartViewModel();
        while (!viewModel.isNetwork()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("You seem to be offline, do you want to retry connection to the server?");
            alert.setTitle("Server unreachable");
            alert.showAndWait();
            if (alert.getResult().getButtonData().isDefaultButton()) {
                viewModel.retryNetwork();
            } else {
                System.exit(0);
            }
        }
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
                if(user.isMaster()) {
                    getViewHandler().openView(View.DASHBOARD);
                    Logger.getInstance().log("Master logged in!");
                } else if (user.isStoreManager()) {
                    if (checkWorkingHours(viewModel.getOpeningHours(), viewModel.getClosingHours()))
                    {
                        getViewHandler().openView(View.DASHBOARD);
                        Logger.getInstance().log("Store Manager logged in!");
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Outside of working hours.");
                        alert.setHeaderText("Use Master Password in order to log in.");
                        alert.showAndWait();
                    }
                } else {
                    // TODO cashier UI and use of it
                    if (checkWorkingHours(viewModel.getOpeningHours(), viewModel.getClosingHours()))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Unimplemented feature");
                        alert.setHeaderText("This feature is not yet implemented.");
                        alert.setContentText("Description: You have logged in as a cashier. It does not have an UI nor a functionality. \n" +
                                "To be able to log out and log in as a master, you need to restart the client app.");
                        alert.showAndWait();

                        // TODO after implementing, remove this alert-box
                        Logger.getInstance().log("Cashier logged in!");
                    }
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

    private boolean checkWorkingHours(String openingHours, String closingHours) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        try {
            Date timeNow = dateFormat.parse(String.valueOf(dtf.format(now)));
            Date openingH = dateFormat.parse(openingHours);
            Date closingH = dateFormat.parse(closingHours);
            if (timeNow.getTime() >= openingH.getTime() && timeNow.getTime() <= closingH.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
