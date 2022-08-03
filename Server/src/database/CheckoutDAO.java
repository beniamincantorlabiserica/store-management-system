package database;

import model.PaymentType;

public interface CheckoutDAO {
    /**
     * @return the next available id for a checkout as an Integer
     */
    Integer getNextAvailableCheckoutNumber();

    void addItemToCheckout(int checkoutId, int itemId);

    Double getTotalForCheckout(int checkoutId);

    void setPaymentType(Integer checkoutId, PaymentType paymentType);

    void rollbackCheckout(Integer checkoutId);
}
