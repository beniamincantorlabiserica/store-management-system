package model;

import java.util.ArrayList;

public interface InventoryModel {
    ArrayList<Item> getItems();

    void changePrice(Long id, Double price);
}
