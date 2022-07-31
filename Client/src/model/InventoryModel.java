package model;

import java.util.ArrayList;

public interface InventoryModel {
    /**
     * @return an ArrayList of the current Items from the database
     */
    ArrayList<Item> getItems();

    void changePrice(Long id, Double price);
    void updateQuantity(int id, int quantity);
}
