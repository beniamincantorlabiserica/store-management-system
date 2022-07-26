package viewmodel;

import model.Item;
import model.Model;

import java.util.ArrayList;

public class CashRegisterViewModel implements CashRegisterViewModelInterface{

    private final Model model;

    public CashRegisterViewModel(Model model) {
        this.model = model;
    }


    @Override
    public ArrayList<Item> scanItem(String barCode) throws RuntimeException {
        return model.scanItem(barCode);
    }

    @Override
    public double checkout() throws RuntimeException {
        return model.checkout();
    }
}
