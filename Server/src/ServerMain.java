import networking.RemoteModel;
import mediator.RemoteModelManager;
import model.ServerModel;
import model.ServerModelManager;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class ServerMain {
    public static void main(String[] args) throws MalformedURLException, RemoteException {
        ServerModel serverModel = new ServerModelManager();
        RemoteModel remoteModel = new RemoteModelManager(serverModel);






    }
}
