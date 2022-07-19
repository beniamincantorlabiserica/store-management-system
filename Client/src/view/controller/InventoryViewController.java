package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logger.Logger;
import logger.LoggerType;
import model.Item;
import view.View;
import view.ViewController;
import viewmodel.InventoryViewModel;

public class InventoryViewController extends ViewController {
    @FXML
    public TableColumn<Item, String> item;
    @FXML
    public TableView<Item> table;
    @FXML
    public TableColumn<Item, Integer> quantity;
    @FXML
    public TableColumn<Item, Integer> price;
    @FXML
    public Button back;
    private InventoryViewModel viewModel;

    /**
     * Called by view handler when view is created for the first time
     *
     * Resets the values of the table rows
     *
     * Sets row's values as Item(name,quantity,price)
     */
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getInventoryViewModel();
        reset();
        item.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Item, Integer>("price"));
    }

    /**
     * Returns to Dashboard
     */
    @FXML
    public void onBackButtonPressed() {
        getViewHandler().openView(View.DASHBOARD);
    }

    /**
     * Initializes a new ObservableList
     *
     * Populates observable list
     *
     * Sets items in the table
     */
    @Override
    public void reset() {
        Logger.getInstance().log(LoggerType.DEBUG, "InventoryViewController -> reset()");
        ObservableList<Item> items = FXCollections.observableArrayList();
        items.addAll(viewModel.getItems());
        table.setItems(items);
    }
}
