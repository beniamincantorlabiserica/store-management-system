package model;

import java.util.ArrayList;

public interface InventoryModel {
    ArrayList<Item> getItems();
    void changePrice(int id, int price);
}
