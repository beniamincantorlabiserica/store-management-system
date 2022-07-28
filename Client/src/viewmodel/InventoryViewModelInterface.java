package viewmodel;

import model.Item;

import java.util.ArrayList;

public interface InventoryViewModelInterface {
    ArrayList<Item> getItems();

    void changePrice(Long id, Double price);
}
