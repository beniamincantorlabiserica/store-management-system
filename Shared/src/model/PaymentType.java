package model;

public enum PaymentType {
    MOBILEPAY("MOBILEPAY"),
    CASH("CASH"),
    CARD("CARD");

    private final String paymentMethod;

    PaymentType(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return paymentMethod;
    }
}
