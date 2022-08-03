package viewmodel;

import javafx.beans.property.SimpleStringProperty;
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
    void onScanPressed();
    void onCheckoutPressed();
    SimpleStringProperty getTotalPriceLabelProperty();
    SimpleStringProperty getScanInputProperty();
    void setTotalPriceLabelProperty(String s);
    ObservableList<ItemTableViewModel> getCurrentItems();

}
