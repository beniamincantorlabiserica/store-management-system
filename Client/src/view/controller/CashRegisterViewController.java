package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logger.Logger;
import logger.LoggerType;
import model.Item;
import view.View;
import view.ViewController;
import viewmodel.CashRegisterViewModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public TableColumn<Item, Double> price;
    @FXML
    public Button checkout;
    @FXML
    public Button scanItem;

    @FXML
    public Button logoutButton;
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
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        item.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
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

        String id = scanInput.getText();
        scanInput.setText("");
        try {
            currentItems = viewModel.scanItem(id);
            Item currentItem = currentItems.get(currentItems.size() - 1);
            currentItems.remove(currentItem);
            if(!addItemToCheckout(currentItem))
            {
                currentItems.add(currentItem);
            }
            reset();
            calculateTotal();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("WRONG_BARCODE"))
            {
                Logger.getInstance().log(LoggerType.DEBUG, "Wrong Barcode");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong Barcode");
                alert.setHeaderText("Please use a valid barcode.");
                alert.showAndWait();
            } else if (e.getMessage().equals("NO_MORE_ITEMS_IN_STOCK"))
            {
                Logger.getInstance().log(LoggerType.DEBUG, "No More Items In Stock");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No More Items In Stock");
                alert.setHeaderText("There are no more items in stock.");
                alert.showAndWait();
            } else {
                Logger.getInstance().log(LoggerType.ERROR, "UNKNOWN EXEPTION ");
            }
        }

    }

    public void onCheckoutPressed() {
        double total = viewModel.checkout();
        currentItems = new ArrayList<>();
        reset();
        totalPrice.setText("0");
        Logger.getInstance().log(LoggerType.DEBUG, "Total price from checkout: " + total);
    }

    public boolean addItemToCheckout(Item item) {
        for (Item i : currentItems) {
           if (item.getId() == i.getId()){
               i.setQuantity(i.getQuantity() + 1);
               return true;
           }
       }
       return false;
    }

    /**
     * The function calculates the total price of the
     * items in the ArrayList and displays it in the totalPrice label
     */
    public void calculateTotal() {
        double sum = 0;
        for (Item item : currentItems) {
            Logger.getInstance().log(LoggerType.DEBUG, "Item: " + item.name);
            sum += item.getPrice() * item.getQuantity();
        }
        totalPrice.setText(String.valueOf(sum));
        Logger.getInstance().log(LoggerType.DEBUG, "----------------------");
    }

    public void logout() {
        viewModel.logout();
        getViewHandler().openView(View.START);
    }

    @Override
    public void reset() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        items.clear();
        items.addAll(currentItems);
        table.setItems(items);
        table.setEditable(true);
        table.refresh();
    }
}
