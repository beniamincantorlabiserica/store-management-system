package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.Model;
import model.PaymentType;

import java.util.ArrayList;

public class CashRegisterViewModel implements CashRegisterViewModelInterface{

    private final Model model;
    ObservableList<ItemTableViewModel> listToReturn;

    public CashRegisterViewModel(Model model) {
        this.model = model;
        this.listToReturn = FXCollections.observableArrayList();
    }


    @Override
    public ObservableList<ItemTableViewModel> scanItem(String barCode) throws RuntimeException {
        listToReturn.clear();
        for(Item item : model.scanItem(barCode)){
            listToReturn.add(new ItemTableViewModel(item));
        }

        return listToReturn;
    }

    @Override
    public double checkout() throws RuntimeException {
        return model.checkout();
    }

    @Override
    public void logout() throws RuntimeException {
        model.logout();
    }

    @Override
    public void completePayment(PaymentType paymentType) throws RuntimeException {
        model.completePayment(paymentType);
    }

    @Override
    public void cancelCheckout() throws RuntimeException {
        model.cancelCheckout();
    }
}
