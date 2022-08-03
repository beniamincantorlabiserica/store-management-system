package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Item;
import model.PaymentType;
import util.logger.Logger;
import util.logger.LoggerType;
import view.View;
import view.ViewController;
import viewmodel.CashRegisterViewModelInterface;
import viewmodel.ItemTableViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CashRegisterViewController extends ViewController {

    @FXML
    public TableView<ItemTableViewModel> table;
    @FXML
    public TableColumn<ItemTableViewModel, String> id;
    @FXML
    public TableColumn<ItemTableViewModel, String> item;
    @FXML
    public TableColumn<ItemTableViewModel, String> quantity;
    @FXML
    public TableColumn<ItemTableViewModel, String> price;
    @FXML
    public TableColumn<ItemTableViewModel, String> priceMark;
    @FXML
    public Button scanItemButton;
    @FXML
    public Label totalPrice;
    @FXML
    public TextField scanInput;
    private CashRegisterViewModelInterface viewModel; // contains a reference to the viewModel


    public CashRegisterViewController() { } // Empty constructor
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getCashRegisterViewModel(); // fetches the viewModel variable from the viewModel factory class
        totalPrice.textProperty().bind(viewModel.getTotalPriceLabelProperty());
        scanInput.textProperty().bindBidirectional(viewModel.getScanInputProperty());
        viewModel.setTotalPriceLabelProperty("0");

        reset();
        id.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        item.setCellValueFactory(cellData -> cellData.getValue().getItemProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().getQuantityProperty());
        price.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
    }

    /**
     * THe function retrieves the id entered by the user,
     * sets the TextField to a null string.
     * It is trying to get the items from the view model,
     * and if there are no exeptions the function calls reset() and
     * calculateTotal() functions, if there is an exeption,
     * the function displays an alert
     */
    public void onScanPressed() {
       viewModel.onScanPressed();
    }

    /**
     * The method displys the options of payment methods
     * to the cashier to choose and then displays a
     * confirmation pop up where the cashier can approve or
     * disapprove the transaction
     */
    public void onCheckoutPressed() {
       viewModel.onCheckoutPressed();
       reset();
    }

    public void onInventoryButtonPressed() {
        getViewHandler().openView(View.INVENTORY);

    }

    public void onLogoutButtonPressed() {
        viewModel.logout();
        getViewHandler().openView(View.START);
    }

    @Override
    public void reset() {
        viewModel.setTotalPriceLabelProperty("0");
        table.setItems(viewModel.getCurrentItems());
        table.setEditable(true);
        table.refresh();
    }
}
