package networking;

import model.PaymentType;

import java.rmi.Remote;

public interface RemoteCashierModel extends Remote {
    void completePayment(PaymentType paymentType);
}
