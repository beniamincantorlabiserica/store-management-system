package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import logger.Logger;
import logger.LoggerType;
import view.View;
import view.ViewController;
import viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void onLogoutButtonPressed() {
        viewModel.logout();
        getViewHandler().openView(View.START);
    }

    @FXML
    public void onInventoryButtonPressed() {
        // TODO inventory page with list of items & ability to edit amount of item x & price of item.
    }

    @FXML
    public void onPasswordsButtonPressed() {
        // TODO change password dialog with dropdown with choice for "master" or "cashier",
        // TODO a master password field (old master password) and two new password
        // TODO fields (new password + confirm new password)
    }

    public void onWorkingHoursButtonPressed() {
        showWorkingHoursChoiceDialog();
    }

    private void showWorkingHoursChoiceDialog() {
        List<String> choices = new ArrayList<>();
        choices.add("opening time");
        choices.add("closing time");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("opening time", choices);
        dialog.setTitle("Working Hours");
        dialog.setContentText("What do you want to edit?");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            boolean valid = false;
            while ((result.get().equals("opening time") || result.get().equals("closing time")) && !valid) {
                TextInputDialog timeDialog;
                if(result.get().contains("open")) {
                    timeDialog = new TextInputDialog(viewModel.getOpeningHours());
                } else {
                    timeDialog = new TextInputDialog(viewModel.getClosingHours());
                }
                timeDialog.setTitle("Working Hours");
                timeDialog.setHeaderText("Enter your desired " + result.get() + ".");
                timeDialog.setContentText("Remember! The format needs to be like 07:00 or 12:00 or 23:35!");
                Optional<String> timeResult = timeDialog.showAndWait();
                if (timeResult.isPresent()){
                    try {
                        if(result.get().equals("opening time")) {
                            viewModel.setOpeningHours(timeResult.get());
                            Logger.getInstance().log(LoggerType.DEBUG, "Opening time set to: " + timeResult.get());
                        } else {
                            viewModel.setClosingHours(timeResult.get());
                            Logger.getInstance().log(LoggerType.DEBUG, "Closing time set to: " + timeResult.get());
                        }
                        Logger.getInstance().log(LoggerType.DEBUG, "Result time: " + timeResult.get());
                        valid = true;
                    } catch (Exception e) {
                        Logger.getInstance().log(LoggerType.ERROR, "Illegal argument for " + result.get());
                    }

                }
            }
        }
    }
}
