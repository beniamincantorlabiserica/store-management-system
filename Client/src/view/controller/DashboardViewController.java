package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import view.View;
import view.ViewController;
import viewmodel.DashboardViewModel;

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
        getViewHandler().openView(View.INVENTORY);
    }

    /**
     * handles the settings button pressing event by opening the settings view
     */
    public void onSettingsButtonPressed() {
        getViewHandler().openView(View.SETTINGS);
    }
}
