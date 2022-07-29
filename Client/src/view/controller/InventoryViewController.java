package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
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
    public TableColumn<Item, Long> id;
    @FXML
    public TableColumn<Item, Integer> quantity;
    @FXML
    public TableColumn<Item, Double> price;
    @FXML
    public Button back;
    private InventoryViewModel viewModel;

    /**
     * Called by view handler when view is created for the first time
     * <p>
     * Resets the values of the table rows
     * <p>
     * Sets row's values as Item(name,quantity,price)
     */
    @Override
    protected void init() {
        viewModel = getViewModelFactory().getInventoryViewModel();
        reset();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        item.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        price.setOnEditCommit(itemIntegerCellEditEvent -> {
            Item item = itemIntegerCellEditEvent.getRowValue();
            viewModel.changePrice(item.getId(), itemIntegerCellEditEvent.getNewValue());
        });
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
     * <p>
     * Populates observable list
     * <p>
     * Sets items in the table
     */
    @Override
    public void reset() {
        Logger.getInstance().log(LoggerType.DEBUG, "InventoryViewController -> reset()");
        ObservableList<Item> items = FXCollections.observableArrayList();
        items.addAll(viewModel.getItems());
        table.setItems(items);
        table.setEditable(true);
    }
}
