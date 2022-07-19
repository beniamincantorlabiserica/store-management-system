package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @Override
    protected void init() {
        ObservableList<Item> items = FXCollections.observableArrayList();
        viewModel = getViewModelFactory().getInventoryViewModel();
        items.addAll(viewModel.getItems());
        table.setItems(items);
        item.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Item, Integer>("price"));
    }

    @FXML
    public void onBackButtonPressed() {
        getViewHandler().openView(View.DASHBOARD);
    }

    @Override
    public void reset() {
    }
}
