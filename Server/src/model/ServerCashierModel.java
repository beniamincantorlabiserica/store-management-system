package model;

public interface ServerCashierModel {
    /**
     * A method to be called after the cashier checks out an order and has the meaning to
     * update the checkout with the correct payment method as specified by the cashier
     *
     * @param paymentType the payment method used
     */
    void completePayment(PaymentType paymentType);
}
