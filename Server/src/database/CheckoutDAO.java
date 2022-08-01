package database;

import model.PaymentType;

public interface CheckoutDAO {
    Integer getNextAvailableCheckoutNumber();

    void addItemToCheckout(int checkoutId, int itemId, String paymentType);

    Double getTotalForCheckout(int checkoutId);

    void setPaymentType(Integer checkoutId, PaymentType paymentType);

    void rollbackCheckout(Integer checkoutId);
}
