package viewmodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.Model;
import model.User;
import util.logger.Logger;
import util.logger.LoggerType;

import java.util.ArrayList;

public class InventoryViewModel implements InventoryViewModelInterface {

    private final Model model;
    private ObservableList<ItemTableViewModel> items;


    public InventoryViewModel(Model model, ViewModelState viewModelState) {
        this.model = model;
        this.items = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<ItemTableViewModel> getItems() {
        items.clear();
        ArrayList<Item> itemsArrayList = new ArrayList<>();
        itemsArrayList = model.getItems();
        for(Item item : itemsArrayList){
            items.add(new ItemTableViewModel(item));
        }
        return items;
    }

    @Override
    public void changePrice(Long id, Double price) {
        if (getUser().isCashier()) {
            Logger.getInstance().log(LoggerType.ERROR, "You need to be a Store Manager to perform this action!");
        } else {
            model.changePrice(id, price);
        }
    }

    @Override
    public void updateQuantity(int id, int quantity) {
        if (!getUser().isCashier()) {
            Logger.getInstance().log(LoggerType.ERROR, "You need to be a Cashier to perform this action!");
        } else {
            model.updateQuantity(id, quantity);
        }
    }

    @Override
    public User getUser() {
        return model.getUser();
    }




}
