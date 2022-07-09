package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import view.View;
import view.ViewController;
import viewmodel.DashboardViewModel;

public class DashboardViewController extends ViewController {

    @FXML
    public Label dateLabel;
    @FXML
    public Label timeLabel;
    @FXML
    public Label dayOfWeekLabel;
    @FXML
    public Label storeStatusLabel;
    @FXML
    public Label checkoutsTodayLabel;
    @FXML
    public Label itemsTodayLabel;
    @FXML
    public Label salesTodayLabel;
    @FXML
    public Label checkoutsThisMonthLabel;
    @FXML
    public Label itemsThisMonthLabel;
    @FXML
    public Label salesThisMonthLabel;
    @FXML
    public ProgressBar dayProgressBar;
    @FXML
    public ProgressBar monthProgressBar;

    private DashboardViewModel viewModel;

    public DashboardViewController() {

    }

    @Override
    protected void init() {
        viewModel = getViewModelFactory().getDashboardViewModel();
        this.dateLabel.textProperty().bind(viewModel.getDateProperty());
        this.timeLabel.textProperty().bind(viewModel.getTimeProperty());
        this.dayOfWeekLabel.textProperty().bind(viewModel.getDayOfWeekProperty());
        this.storeStatusLabel.textProperty().bind(viewModel.getStoreStatusProperty());
        this.checkoutsTodayLabel.textProperty().bind(viewModel.getCheckoutsTodayProperty());
        this.itemsTodayLabel.textProperty().bind(viewModel.getItemsTodayProperty());
        this.salesTodayLabel.textProperty().bind(viewModel.getSalesTodayProperty());
        this.checkoutsThisMonthLabel.textProperty().bind(viewModel.getCheckoutsThisMonthProperty());
        this.itemsThisMonthLabel.textProperty().bind(viewModel.getItemsThisMonthProperty());
        this.salesThisMonthLabel.textProperty().bind(viewModel.getSalesThisMonthProperty());
        this.dayProgressBar.progressProperty().bind(viewModel.getDayProgressBarProperty());
        this.monthProgressBar.progressProperty().bind(viewModel.getMonthProgressBarProperty());
    }

    @Override
    public void reset() {
        viewModel.reset();
    }

    @FXML
    public void onLogoutButtonPressed(ActionEvent actionEvent) {
        viewModel.logout();
        getViewHandler().openView(View.START);
    }

    @FXML
    public void onInventoryButtonPressed(ActionEvent actionEvent) {
        // TODO inventory page with list of items & ability to edit amount of item x & price of item.
    }

    @FXML
    public void onPasswordsButtonPressed(ActionEvent actionEvent) {
        // TODO change password dialog with dropdown with choice for "master" or "cashier",
        // TODO a master password field (old master password) and two new password
        // TODO fields (new password + confirm new password)
    }
}
