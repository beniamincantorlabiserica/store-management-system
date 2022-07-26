package viewmodel;

import model.Item;

import java.util.ArrayList;

public interface CashRegisterViewModelInterface {
    ArrayList<Item> scanItem(String barCode) throws RuntimeException;
    double checkout() throws RuntimeException;
}
