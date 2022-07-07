package client.mediator;



import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientUserModel {

    boolean login (String password);

}
