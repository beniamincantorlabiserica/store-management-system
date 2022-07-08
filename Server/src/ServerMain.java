import networking.RemoteModel;
import mediator.ServerNetworkManager;
import model.ServerModel;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class ServerMain {
    public static void main(String[] args) throws MalformedURLException, RemoteException {
        ServerModel serverModel = new model.ServerModelManager();
        RemoteModel remoteModel = new ServerNetworkManager(serverModel);
    }
}
