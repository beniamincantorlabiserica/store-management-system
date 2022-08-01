package model;

public interface ServerCashierModel {
    /**
     * marks the current checkout as complete by assigning to it a valid payment method
     *
     * @param paymentType the payment method used
     */
    void completePayment(PaymentType paymentType);
}
