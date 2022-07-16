package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import logger.Logger;
import logger.LoggerType;
import view.View;
import view.ViewController;
import viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * contains the view controller components related to the dashboard
 */
public class DashboardViewController extends ViewController {
    @FXML
    public Label dateLabel; // contains a string representing today's date
    @FXML
    public Label timeLabel; // contains a string representing the time now
    @FXML
    public Label dayOfWeekLabel; // contains a string representing the day of the week
    @FXML
    public Label storeStatusLabel; // contains a string representing 'CLOSED' if the store is outside configured working hours and 'OPEN' if between
    @FXML
    public Label checkoutsTodayLabel; // contains a string representing the number of unique checkouts that were registered today
    @FXML
    public Label itemsTodayLabel; // contains a string representing the total number of items sold from all the checkouts today
    @FXML
    public Label salesTodayLabel; // contains a string representing the amount of currency the total items sold today sums up to
    @FXML
    public Label checkoutsThisMonthLabel; // contains a string representing the number of unique checkouts that were registered this month from the first day of the month to the last
    @FXML
    public Label itemsThisMonthLabel; // contains a string representing the total number of items sold from all the checkouts this month from the first day of the month to the last
    @FXML
    public Label salesThisMonthLabel; // contains a string representing the amount of currency the total items sold this month from the first day of the month to the last sums up to
    @FXML
    public ProgressBar dayProgressBar; // contains a value representing the hours passed until the present moment between the opening hour and the closing hour
    @FXML
    public ProgressBar monthProgressBar; // contains a value representing the days passed until the present moment between the first and the last days of the current month

    private DashboardViewModel viewModel; // contains a reference to the viewModel

    public DashboardViewController() {} // empty constructor

    /**
     * called by the view handler when creating the view for the first time
     *
     * fetches the viewModel corresponding to this view
     *
     * binds the FXML variables to the property variables from the viewModel
     *
     * starts the view update thread in the viewModel (used to update the time, date and day of the week elements from this view)
     *
     * performs a viewModel reset so that all the appropriate data from the model is fetched just before the user is able to see it
     */
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getDashboardViewModel(); // fetches the viewModel variable from the viewModel factory class
        this.dateLabel.textProperty().bind(viewModel.getDateProperty()); // ->
        this.timeLabel.textProperty().bind(viewModel.getTimeProperty());
        this.dayOfWeekLabel.textProperty().bind(viewModel.getDayOfWeekProperty());
        this.storeStatusLabel.textProperty().bind(viewModel.getStoreStatusProperty());
        this.checkoutsTodayLabel.textProperty().bind(viewModel.getCheckoutsTodayProperty());
        this.itemsTodayLabel.textProperty().bind(viewModel.getItemsTodayProperty());
        this.salesTodayLabel.textProperty().bind(viewModel.getSalesTodayProperty());
        this.checkoutsThisMonthLabel.textProperty().bind(viewModel.getCheckoutsThisMonthProperty());
        this.itemsThisMonthLabel.textProperty().bind(viewModel.getItemsThisMonthProperty());
        this.salesThisMonthLabel.textProperty().bind(viewModel.getSalesThisMonthProperty());
        this.dayProgressBar.progressProperty().bindBidirectional(viewModel.getDayProgressBarProperty());
        this.monthProgressBar.progressProperty().bindBidirectional(viewModel.getMonthProgressBarProperty()); // <- binding the variables to the corresponding viewModel property
        viewModel.startUpdateThread(); // starts a thread in the viewModel, with the purpose explained in this method's JavaDOC
        viewModel.reset(); // performs a viewModel reset to update data to the newest version available
    }

    /**
     * used to perform a reset into the viewModel, to update the data to the newest available version from the model
     */
    @Override
    public void reset() {
        viewModel.reset();
    }

    /**
     * handles the logout button pressing event by sending a logout signal to the viewModel and opens the start view
     */
    @FXML
    public void onLogoutButtonPressed() {
        viewModel.logout();
        getViewHandler().openView(View.START);
    }

    /**
     * handles the inventory button pressing event by sending displaying a window containing a list of items with three corresponding buttons
     * for each item with the following functionality:
     *  - 'Edit Amount' button: displays a Text Input Dialog with a text field input with the default value of the number of items available at
     *  the moment in the store; after the user enters a number and presses ok, the number is passed to the viewModel in a corresponding function.
     *  if the inputted text is not a number or contains spaces, display an alert box stating the error and let the user try again
     *  - 'Edit Price' button: displays a Text Input Dialog with a text field input with the default value of the current price of the item; after
     *  the user enters a number and presses ok, the number is passed to the viewModel in a corresponding function.
     *  if the inputted text is not a number or contains spaces, display an alert box stating the error and let the user try again
     *  - 'Remove Item' button: displays a Confirmation Dialog stating that the item will be removed if the user presses 'YES' and
     *  it won't be removed if the user presses 'NO'
     */
    @FXML
    public void onInventoryButtonPressed() {
        // TODO inventory page with list of items & ability to edit amount of item x & price of item.
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Unimplemented feature");
        alert.setHeaderText("This feature is not yet implemented.");
        alert.setContentText("Description: inventory page with list of items & ability to edit amount of item x & price of item.");
        alert.showAndWait();
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
            if(!showMasterPasswordCheckDialog()) {
                Logger.getInstance().log(LoggerType.ERROR, "Wrong master password");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Wrong Master Password");
                alert.setContentText("The password you entered was wrong.");
                alert.showAndWait();
                return;
            }
            Logger.getInstance().log("Master password OK");
            if(choice.equals("Store Manager") || choice.equals("Cashier")){
                try {
                    viewModel.updatePassword(choice, showPasswordChangeDialog(choice));
                } catch (Exception e) {
                    if(e.getMessage().contains("PASSWORD")) {
                        Alert alert = new Alert(AlertType.ERROR);
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
            if (!validateNewPassword(result.get())) { // validate the entered password
                throw new RuntimeException("PASSWORD_INVALID");
            }
            return result.get();
        }
        throw new RuntimeException("DIALOG_CANCELLED");
    }

    /**
     * validates the password sent as a string parameter
     * @param s expects the password to be validated
     * @return true if the password passes the validation tests, false otherwise
     */
    private boolean validateNewPassword(String s) {
        if(s.length() < 4) {
            Logger.getInstance().log(LoggerType.ERROR, "The new password should contain more than 3 characters");
            return false;
        }
        return true;
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
            boolean valid = false; // var to keep displaying the Text Input Dialog if the text from the text field is not valid
            while ((result.get().equals("opening time") || result.get().equals("closing time")) && !valid) {
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
                        valid = true; // once the input was sent to the viewModel and no invalidation exception occurs, the dialog can exit
                    } catch (Exception e) {
                        Logger.getInstance().log(LoggerType.ERROR, "Illegal argument for " + result.get());
                    }
                }
            }
        }
    }
}
