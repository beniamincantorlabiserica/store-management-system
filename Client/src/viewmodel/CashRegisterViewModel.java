package viewmodel;

import model.Item;
import model.Model;
import model.PaymentType;

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
