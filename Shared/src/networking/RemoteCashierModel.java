package networking;

import model.PaymentType;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteCashierModel extends Remote {
    /**
     * A method to be called after the cashier checks out an order and has the meaning to
     * update the checkout with the correct payment method as specified by the cashier
     *
     * @param paymentType needs to provide a valid PaymentType argument
     * @throws RemoteException currently not thrown
     */
    void completePayment(PaymentType paymentType) throws RemoteException;
}
