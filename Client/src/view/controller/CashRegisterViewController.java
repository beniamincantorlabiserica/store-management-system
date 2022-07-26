package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logger.Logger;
import logger.LoggerType;
import model.Item;
import view.ViewController;
import viewmodel.CashRegisterViewModel;

import java.util.ArrayList;

public class CashRegisterViewController extends ViewController {

    @FXML
    public TableView<Item> table;
    @FXML
    public TableColumn<Item, Integer> id;
    @FXML
    public TableColumn<Item, String> item;
    @FXML
    public TableColumn<Item, Integer> quantity;
    @FXML
    public TableColumn<Item, Integer> price;
    @FXML
    public Button checkout;
    @FXML
    public Button scanItem;
    @FXML
    public Label totalPrice;
    @FXML
    public TextField scanInput;
    private CashRegisterViewModel viewModel; // contains a reference to the viewModel
    private ArrayList<Item> currentItems;

    public CashRegisterViewController() { } // Empty constructor
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getCashRegisterViewModel(); // fetches the viewModel variable from the viewModel factory class
        totalPrice.setText("0");
        currentItems = new ArrayList<>();
        reset();
        id.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));
        item.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Item, Integer>("price"));
    }

    public void onScanPressed() {

        String id = scanInput.getText();
        scanInput.setText("");
        currentItems = viewModel.scanItem(id);
        reset();
        calculateTotal();
    }

    public void onCheckoutPressed() {

    }

    public void calculateTotal() {
        double sum = 0;
        for (Item item : currentItems) {
            Logger.getInstance().log(LoggerType.DEBUG, "Item: " + item.name);
            sum += item.getPrice();
        }
        totalPrice.setText(String.valueOf(sum));
        Logger.getInstance().log(LoggerType.DEBUG, "----------------------");
    }

    @Override
    public void reset() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        items.addAll(currentItems);
        table.setItems(items);
        table.setEditable(true);
    }
}
