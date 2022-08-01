package model;

import database.connection.DBConnection;
import database.factory.DAOFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServerModelManagerTest {

    private ServerModelManager serverModelManager;

    @BeforeEach
    void setUp() {
        serverModelManager = new ServerModelManager(
                new DAOFactory(new DBConnection())
        );
    }

    @Test
    void shouldThrowWrongBarcodeException() {
        RemoteException exception = assertThrows(RemoteException.class,
                () -> serverModelManager.scanItem("153")
        );
        assertEquals("WRONG_BARCODE", exception.getMessage());
    }

    @Test
    void shouldThrowNoItemInStockException() {
        RemoteException exception = assertThrows(RemoteException.class,
                () -> serverModelManager.scanItem("4")
        );
        assertEquals("NO_MORE_ITEMS_IN_STOCK", exception.getMessage());
    }
}