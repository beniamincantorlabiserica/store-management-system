package viewmodel;

import logger.Logger;
import logger.LoggerType;
import model.Item;
import model.Model;

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
    public void changePrice(Long id, Double price, String role) {
        if(role.equals("cashier")){
            Logger.getInstance().log(LoggerType.ERROR, "You need to be a Master to perform this action!");
        }else {
            model.changePrice(id, price);
        }
    }

    @Override
    public void updateQuantity(int id, int quantity, String role) {
        if(role.equals("cashier")) {
            model.updateQuantity(id, quantity);
        }else {
            Logger.getInstance().log(LoggerType.ERROR, "You need to be a Cashier to perform this action!");
        }
    }


}
