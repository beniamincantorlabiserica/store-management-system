package viewmodel;

import logger.Logger;
import logger.LoggerType;
import model.Item;
import model.Model;
import model.User;

import java.util.ArrayList;

public class InventoryViewModel implements InventoryViewModelInterface {

    private final Model model;

    public InventoryViewModel(Model model, ViewModelState viewModelState) {
        this.model = model;
    }

    @Override
    public ArrayList<Item> getItems() {
        return model.getItems();
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
