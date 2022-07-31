package model;

import java.util.ArrayList;

public interface ServerInventoryModel {
    ArrayList<Item> getItems();

    void changePrice(Long id, Double price);
    void updateQuantity(int id, int quantity);
}
