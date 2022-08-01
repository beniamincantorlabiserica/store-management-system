package database;

import model.Item;

import java.util.ArrayList;

public interface InventoryDAO {
    ArrayList<Item> getItems();

    void changePrice(Long id, Double price);

    Item isItem(int id);

    void updateQuantity(int id, int quantity);
}
