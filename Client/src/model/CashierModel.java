package model;

import java.util.ArrayList;

public interface CashierModel {
    ArrayList<Item> scanItem(String barCode) throws RuntimeException;

    void completePayment(PaymentType paymentType) throws RuntimeException;

    Double checkout() throws RuntimeException;
}
