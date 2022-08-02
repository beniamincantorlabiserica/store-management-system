package viewmodel;

import javafx.collections.ObservableList;
import model.Item;
import model.PaymentType;

import java.util.ArrayList;

public interface CashRegisterViewModelInterface {
    ObservableList<ItemTableViewModel> scanItem(String barCode) throws RuntimeException;
    double checkout() throws RuntimeException;
    void logout() throws RuntimeException;
    void completePayment(PaymentType paymentType) throws RuntimeException;
    void cancelCheckout() throws RuntimeException;
}
