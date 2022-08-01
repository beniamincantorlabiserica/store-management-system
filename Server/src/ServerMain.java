import database.DAOManager;
import database.connection.DBConnection;
import database.factory.DAOFactory;
import mediator.ServerNetworkManager;
import model.ServerModel;
import model.ServerModelManager;
import util.logger.Logger;
import util.logger.LoggerType;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ServerMain {
    private static final int SERVER_PORT = 1099;
    private static ServerModel model;

    public static void main(String[] args) {
        startServerThread();
        startConsoleThread();
    }

    private static void startConsoleThread() {
        Thread consoleThread = new Thread(() -> {
            String command;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                command = scanner.nextLine();
                if (command.equals("/exit")) {
                    break;
                }
                if (command.equals("/soft-restart")) {
                    model.softRestart();
                } else if (command.equals("/help")) {
                    System.out.println("/exit --> Closes the connection to the database and performs system power off.");
                    System.out.println("/soft-restart --> Attempts to flush the local data and reconnect to the database without disconnecting the RMI clients.");
                    System.out.println("/restart --> Closes the connection to the database and performs system reboot.");
                } else {
                    Logger.getInstance().log(LoggerType.WARNING, "Unknown command! Please try one of the following: /exit, /soft-restart, or type /help to see explanations for each command.");
                }
            }
            System.exit(0);
        });
        consoleThread.start();
    }

    private static void startServerThread() {
        DBConnection dbConnection = new DBConnection();
        DAOManager daoManager = new DAOFactory(dbConnection);
        model = new ServerModelManager(daoManager);
        Thread serverThread = new Thread(() -> {
            try {
                new ServerNetworkManager(model, SERVER_PORT);
            } catch (RemoteException | MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
    }
}
