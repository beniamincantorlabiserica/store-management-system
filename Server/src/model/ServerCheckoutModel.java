package model;

import java.rmi.RemoteException;

public interface ServerCheckoutModel {
    /**
     * A method to be called when the cashier scans an item
     *
     * @param barCode needs to provide a valid item id as a String
     * @return an ArrayList of the current Items in the checkout
     * @throws RemoteException if the barcode is not found as WRONG_BARCODE
     *                         or if there are no more items for the current barcode as NO_MORE_ITEMS_IN_STOCK
     */
    Item scanItem(String barCode) throws RemoteException;

    /**
     * A method to be called after the cashier finishes scanning all the items and wants to
     * get the total
     *
     * @return the total amount for the current checkout
     * @throws RemoteException if there are no items in the current checkout
     */
    Double checkout() throws RemoteException;

    /**
     * A method to be called if the cashier wants to cancel the checkout being currently
     * in progress (e.g. the customer can not pay / the customer doesn't want to buy the items for some reason)
     */
    void cancelCheckout();

    /**
     * A method to be used if the cashier wants to undo an already completed checkout
     * (e.g. customer wants the money back, the checkout was done for testing purposes)
     *
     * @param checkoutId the id of the checkout needed to be undone
     */
    void cancelCheckout(Integer checkoutId);
}
