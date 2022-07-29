package model;

import java.util.ArrayList;

public interface CashierModel {
    /**
     * A method to be called when the cashier scans an item
     *
     * @param barCode needs to provide a valid item id as a String
     * @return an ArrayList of the current Items in the checkout
     * @throws RuntimeException if the barcode is not found as WRONG_BARCODE
     *                          or if there are no more items for the current barcode as NO_MORE_ITEMS_IN_STOCK
     */
    ArrayList<Item> scanItem(String barCode) throws RuntimeException;

    /**
     * A method to be called after the cashier checks out an order and has the meaning to
     * update the checkout with the correct payment method as specified by the cashier
     *
     * @param paymentType needs to provide a valid PaymentType argument
     * @throws RuntimeException currently not thrown
     */
    void completePayment(PaymentType paymentType) throws RuntimeException;

    /**
     * A method to be called after the cashier finishes scanning all the items and wants to
     * get the total
     *
     * @return the total amount for the current checkout
     * @throws RuntimeException if there are no items in the current checkout
     */
    Double checkout() throws RuntimeException;

    /**
     * A method to be called if the cashier wants to cancel the checkout being currently
     * in progress (e.g. the customer can not pay / the customer doesn't want to buy the items for some reason)
     *
     * @throws RuntimeException currently not thrown
     */
    void cancelCheckout() throws RuntimeException;

    /**
     * A method to be used if the cashier wants to undo an already completed checkout
     * (e.g. customer wants the money back, the checkout was done for testing purposes)
     *
     * @param checkoutId the id of the checkout needed to be undone
     * @throws RuntimeException if the checkoutId is not found
     */
    void undoCheckout(Integer checkoutId) throws RuntimeException;
}
