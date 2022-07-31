package viewmodel;

import model.Item;
import model.User;

import java.util.ArrayList;

public interface InventoryViewModelInterface {
    ArrayList<Item> getItems();

    void changePrice(Long id, Double price);

    void updateQuantity(int id, int quantity);

    User getUser();
}
