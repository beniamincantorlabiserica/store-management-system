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
import java.util.Optional;


public class StartViewController extends ViewController {
    @FXML
    public Label passwordLabel; // holds the reference to the password label
    @FXML
    public Label titleLabel; // holds the reference to the title label
    @FXML
    public Button mainButton; // holds the reference to the main button
    private StartViewModel viewModel; // contains a reference to the corresponding viewModel
    @FXML
    private PasswordField passwordField; // holds the reference to the password field

    /**
     * default no-args constructor
     */
    public StartViewController() {
    }

    /**
     * ! always call this method after creating !
     * receives a reference to the corresponding viewModel
     * checks for network connection to the server and, if not present, asks the user to retry, otherwise close the app
     */
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

    /**
     * resets the viewController to its default state
     */
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
                    if (viewModel.isOpen())
                    {
                        getViewHandler().openView(View.CASH_REGISTER);
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
