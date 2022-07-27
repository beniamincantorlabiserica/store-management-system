package database;

import logger.Logger;
import logger.LoggerType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckoutDatabaseManager {
    private final DBConnection connection;

    public CheckoutDatabaseManager(DBConnection connection) {
        this.connection = connection;
    }

    public Integer getNextAvailableCheckoutNumber() {
        Logger.getInstance().log(LoggerType.DEBUG, "getNextAvailableCheckoutNumber()");
        String query = "SELECT MAX(id) AS currentId FROM checkouts";
        ResultSet rs;
        rs = connection.queryDB(query);
        try {
            rs.next();
            return rs.getInt("currentId") + 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItemToCheckout(int checkoutId, int itemId, String paymentType) {
        Logger.getInstance().log(LoggerType.DEBUG, "addItemToCheckout(" + checkoutId + ", " + itemId + ")");
        if (isItemInCheckout(checkoutId, itemId)) {
            addExistingItemToCheckout(checkoutId, itemId);
        } else {
            addNewItemToCheckout(checkoutId, itemId, paymentType);
        }
    }

    private void addNewItemToCheckout(int checkoutId, int itemId, String paymentType) {
        Logger.getInstance().log(LoggerType.DEBUG, "addNewItemToCheckout(" + checkoutId + ", " + itemId + ")");
        String query = "INSERT INTO checkouts VALUES (" + checkoutId + ", " + itemId + ", 1, '" + paymentType + "')";
        connection.updateDB(query);
    }

    private void addExistingItemToCheckout(int checkoutId, int itemId) {
        Logger.getInstance().log(LoggerType.DEBUG, "addExistingItemToCheckout(" + checkoutId + ", " + itemId + ")");
        String query = "UPDATE checkouts SET quantity = quantity + 1 WHERE id = " + checkoutId + " AND itemid = " + itemId;
        connection.updateDB(query);
    }

    private boolean isItemInCheckout(int checkoutId, int itemId) {
        Logger.getInstance().log(LoggerType.DEBUG, "isItemInCheckout(" + checkoutId + ", " + itemId + ")");
        String query = "SELECT id FROM checkouts WHERE id = " + checkoutId + " AND itemid = " + itemId;
        ResultSet rs = connection.queryDB(query);
        try {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getTotalForCheckout(int checkoutId) {
        Logger.getInstance().log(LoggerType.DEBUG, "getTotalForCheckout(" + checkoutId + ")");
        String query = "SELECT " +
                "ck.id, " +
                "SUM(ck.quantity * i.price) AS total " +
                "FROM checkouts ck " +
                "JOIN items i " +
                "ON i.id = ck.itemid " +
                "WHERE ck.id = " + checkoutId + " " +
                "GROUP BY ck.id";
        ResultSet rs = connection.queryDB(query);
        try {
            rs.next();
            return rs.getDouble("total");
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Database error when trying to get totals for checkout " + checkoutId);
            throw new RuntimeException(e);
        }
    }
}