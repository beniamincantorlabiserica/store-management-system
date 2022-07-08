package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    @Override
    protected void init() {

    }

    @Override
    public void reset() {

    }

    @FXML
    public void onLogoutButtonPressed(ActionEvent actionEvent) {

    }

    @FXML
    public void onInventoryButtonPressed(ActionEvent actionEvent) {

    }

    @FXML
    public void onPasswordsButtonPressed(ActionEvent actionEvent) {

    }
}
