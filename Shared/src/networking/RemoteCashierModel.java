package networking;

import model.PaymentType;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteCashierModel extends Remote {
    void completePayment(PaymentType paymentType) throws RemoteException;
}
