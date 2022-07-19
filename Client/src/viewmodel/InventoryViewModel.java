package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.Item;
import model.Model;

import java.util.ArrayList;

public class InventoryViewModel implements InventoryViewModelInterface {

    private final Model model;
//    @FXML
//    public TableView<Item> table;

    public InventoryViewModel(Model model, ViewModelState viewModelState) {
        this.model = model;
    }

    @Override
    public ArrayList<Item> getItems() {
        return model.getItems();
    }
    @Override
    public void reset() {
//        ObservableList<Item> items = FXCollections.observableArrayList();
//        items.addAll(model.getItems());
//        table.setItems(items);
    }
}
