package viewmodel;

import javafx.collections.ObservableList;
import model.Item;
import model.User;

import java.util.ArrayList;

public interface InventoryViewModelInterface {
    ObservableList<ItemTableViewModel> getItems();

    void changePrice(Long id, Double price);

    void updateQuantity(int id, int quantity);

    User getUser();
}
