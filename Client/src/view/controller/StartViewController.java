package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logger.Logger;
import logger.LoggerType;
import model.User;
import view.View;
import view.ViewController;
import viewmodel.StartViewModel;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;


public class StartViewController extends ViewController {
    private StartViewModel viewModel;
    @FXML
    public Label passwordLabel;
    @FXML
    public Label titleLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    public Button mainButton;

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
        checkLocked();
    }

    /**
     * performs a check to see if the store should be locked or not and applies the
     * correct visual changes
     */
    private void checkLocked() {
        try {
            if(viewModel.getLockedState()) {
                Logger.getInstance().log(LoggerType.DEBUG, "Store is locked, applying changes..");
                titleLabel.setText("STORE LOCKED");
                passwordField.visibleProperty().set(false);
                passwordLabel.visibleProperty().set(false);
                mainButton.setText("UNLOCK");
            } else {
                Logger.getInstance().log(LoggerType.DEBUG, "Store is unlocked, applying changes..");
                titleLabel.setText("STORE");
                passwordField.visibleProperty().set(true);
                passwordLabel.visibleProperty().set(true);
                mainButton.setText("Login");
            }
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Error when fetching locked state: " + e.getMessage());
        }
    }

    @Override
    public void reset() {
        passwordField.clear();
        viewModel.reset();
        checkLocked();
    }

    /**
     * handles the button pressing event, dynamically switching between the modes of the button,
     * between unlock button or log in button, depending on the status of the store
     */
    public void onMainButtonPressed() {
        try {
            if(viewModel.getLockedState()) {
                boolean knowsMasterPassword = showMasterPasswordCheckDialog();
                if(!knowsMasterPassword) {
                    Logger.getInstance().log(LoggerType.ERROR, "Wrong master password");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wrong Master Password");
                    alert.setContentText("The password you entered was wrong.");
                    alert.showAndWait();
                    return;
                } else {
                    Logger.getInstance().log("Unlocking successful");
                    viewModel.setLockedState(false);
                    reset();
                }
                return;
            }
        } catch (RemoteException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Error when fetching locked state: " + e.getMessage());
            return;
        }

        User user;
        try {
            user = viewModel.login(passwordField.getText());
            if (user != null) {
                if(user.isMaster()) {
                    getViewHandler().openView(View.DASHBOARD);
                    Logger.getInstance().log("Master logged in!");
                } else if (user.isStoreManager()) {
                    Logger.getInstance().log(LoggerType.DEBUG, viewModel.getOpeningHours() + " --> " + viewModel.getClosingHours());
                    if (viewModel.isOpen())
                    {
                        getViewHandler().openView(View.DASHBOARD);
                        Logger.getInstance().log("Store Manager logged in!");
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Outside of working hours.");
                        alert.setHeaderText("Use Master Password in order to log in.");
                        alert.showAndWait();
                        viewModel.logout();
                    }
                } else {
                    // TODO cashier UI and use of it
                    if (viewModel.isOpen())
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Unimplemented feature");
                        alert.setHeaderText("This feature is not yet implemented.");
                        alert.setContentText("Description: You have logged in as a cashier. It does not have an UI nor a functionality. \n" +
                                "To be able to log out and log in as a master, you need to restart the client app.");
                        alert.showAndWait();
                        viewModel.logout();
                        // TODO after implementing, remove this alert-box
                        Logger.getInstance().log("Cashier logged in!");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Outside of working hours.");
                        alert.setHeaderText("Use Master Password in order to log in.");
                        alert.showAndWait();
                        viewModel.logout();
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

    /**
     * This method is checking if the current time is in between the working
     * hours of the system.
     * @param openingHours takes in the opening hours as string
     * @param closingHours takes in the closing hours as string
     * The parameters are converted into Date type and compared with the current time
     * which is also converted to Date type
     * @return boolean (true - if the current time is in  between the working hours,
     *                  false - if the current time is not in between the  working hours)
     */
    @Deprecated
    private boolean checkWorkingHours(String openingHours, String closingHours) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        try {
            Date timeNow = dateFormat.parse(dtf.format(now));
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

    /**
     * displays a dialog with a text field input expecting the correct master password
     * @return true if the master password matches, false otherwise
     */
    private boolean showMasterPasswordCheckDialog() {
        TextInputDialog dialog = new TextInputDialog("Master password...");
        dialog.setTitle("Master Password Check");
        dialog.setContentText("Please enter the master password:");
        Optional<String> result = dialog.showAndWait();
        return result.isPresent() && viewModel.masterCheck(result.get()); // checks the provided dialog password against the master password from the database
    }
}
