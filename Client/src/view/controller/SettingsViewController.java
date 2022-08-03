package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import util.logger.Logger;
import util.logger.LoggerType;
import view.View;
import view.ViewController;
import viewmodel.SettingsViewModelInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SettingsViewController extends ViewController {

    private SettingsViewModelInterface viewModel; // contains a reference to the viewModel

    /**
     * ! always call this method after creating !
     * receives a reference to the corresponding viewModel
     */
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getSettingsViewModel(); // fetches the viewModel variable from the viewModel factory class
    }

    /**
     * resets the viewController to its default state
     */
    @Override
    public void reset() {
    }

    /**
     * opens the dashboard view and closes the settings view.
     */
    public void onBackButtonPressed() {
        getViewHandler().openView(View.DASHBOARD);
    }

    /**
     * handles the passwords button pressing event by displaying a Confirmation Dialog with Custom Actions with the buttons for changing the password for the
     * 'Master' or for the 'Cashier', and a 'Cancel' button to be able to exit.
     * if the choice is not the cancel button, a new Custom Change Password Dialog is shown with three text field inputs ('Master Password', 'Old Password', 'New Password')
     * and two buttons ('Change Password' and 'Cancel').
     * if the data from the text fields is validated, pass the password change desire to the viewModel, else show an Error Alert box and let the user retry
     */
    @FXML
    public void onPasswordsButtonPressed() {
        List<String> choices = new ArrayList<>();
        choices.add("Store Manager"); // ->
        choices.add("Cashier"); // <- choices for the Choice Dialog

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Store Manager", choices); // ->
        dialog.setTitle("Password Change");
        dialog.setContentText("What password do you want to change?"); // <- preferences for the Choice Dialog
        Optional<String> result = dialog.showAndWait(); // show the dialog and wait for an answer
        if (result.isPresent()) {
            String choice = result.get();
            if(userKnowsMasterPassword()) {
                return;
            }
            Logger.getInstance().log("Master password OK");
            if(choice.equals("Store Manager") || choice.equals("Cashier")){
                try {
                    viewModel.updatePassword(choice, showPasswordChangeDialog(choice));
                } catch (Exception e) {
                    if(e.getMessage().contains("PASSWORD")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Password Invalid");
                        alert.setContentText("The password should have more than 3 characters.");
                        alert.showAndWait();
                    }
                }
            }
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

    /**
     * @param role expects the role for which the password will be changed (cashier or storeManager)
     * @return the new password for the specified role
     */
    private String showPasswordChangeDialog(String role) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New " + role + " password");
        dialog.setContentText("Please enter the desired new password for the " + role + ":");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            String newPassword = result.get();
            viewModel.validateNewPassword(newPassword);
            return newPassword;
        }
        throw new RuntimeException("DIALOG_CANCELLED");
    }


    /**
     * handles the working hours button pressing event by displaying a Choice Dialog with a dropdown input (with the ability to choose from editing the opening time or
     * the closing time) and a 'Cancel' button; if the user presses OK, Choice Dialog is closed and a new Text Input Dialog is shown, where the user needs to enter the new
     * closing / opening time in the format 'HH:mm', HH - hour in 24h format, mm - minutes)
     */
    public void onWorkingHoursButtonPressed() {
        showWorkingHoursChoiceDialog();
    }

    /**
     * extracted implementation for the onWorkingHoursButtonPressed
     */
    private void showWorkingHoursChoiceDialog() {
        List<String> choices = new ArrayList<>();
        choices.add("opening time"); // ->
        choices.add("closing time"); // <- choices for the Choice Dialog

        ChoiceDialog<String> dialog = new ChoiceDialog<>("opening time", choices); // ->
        dialog.setTitle("Working Hours");
        dialog.setContentText("What do you want to edit?"); // <- preferences for the Choice Dialog
        Optional<String> result = dialog.showAndWait(); // show the dialog and wait for an answer
        if (result.isPresent()) {
            if ((result.get().equals("opening time") || result.get().equals("closing time"))) {
                TextInputDialog timeDialog;
                if (result.get().contains("open")) {
                    timeDialog = new TextInputDialog(viewModel.getOpeningHours()); // set the dialog default value according to the desired edit
                } else {
                    timeDialog = new TextInputDialog(viewModel.getClosingHours()); // set the dialog default value according to the desired edit
                }
                timeDialog.setTitle("Working Hours"); // ->
                timeDialog.setHeaderText("Enter your desired " + result.get() + ".");
                timeDialog.setContentText("Remember! The format needs to be like 07:00 or 12:00 or 23:35!"); // <- preferences for the Text Input Dialog
                Optional<String> timeResult = timeDialog.showAndWait(); // show the dialog and wait for an answer
                if (timeResult.isPresent()) {
                    try { // put the results catching into a try block to catch additional validation erros for the input
                        if (result.get().equals("opening time")) {
                            viewModel.setOpeningHours(timeResult.get());
                            Logger.getInstance().log(LoggerType.DEBUG, "Opening time set to: " + timeResult.get());
                        } else {
                            viewModel.setClosingHours(timeResult.get());
                            Logger.getInstance().log(LoggerType.DEBUG, "Closing time set to: " + timeResult.get());
                        }
                        Logger.getInstance().log(LoggerType.DEBUG, "Result time: " + timeResult.get());
                    } catch (Exception e) {
                        Logger.getInstance().log(LoggerType.ERROR, "Illegal argument for " + result.get());
                    }
                }
            }
        }
    }

    /**
     * handles the force lock button pressed event and starts the procedure to attempt force locking of the store
     */
    public void onForceLockButtonPressed() {
        if (userKnowsMasterPassword()) {
            return;
        }
        viewModel.setLockedState(true);
        getViewHandler().openView(View.START);
        Logger.getInstance().log("Force locking...");
        Logger.getInstance().log(LoggerType.WARNING, "The master password has been used to force lock the store!");
    }

    /**
     * @return true if user enters the correct master password in a dialog, false otherwise
     */
    private boolean userKnowsMasterPassword() {
        if (!showMasterPasswordCheckDialog()) {
            Logger.getInstance().log(LoggerType.ERROR, "Wrong master password");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Master Password");
            alert.setContentText("The password you entered was wrong.");
            alert.showAndWait();
            return true;
        }
        return false;
    }
}
