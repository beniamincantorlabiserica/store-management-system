package database;

import logger.Logger;
import logger.LoggerType;
import model.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InventoryDatabaseManager {
    private final Connection connection;
    Statement statement = null;

    public InventoryDatabaseManager(Connection connection) {
        this.connection = connection;
    }


    /**
     * @return an ArrayList of items from database
     */
    public ArrayList<Item> getItems() {

        ArrayList<Item> items = new ArrayList<>();
        try {

            String query = "Select * from items";
            ResultSet rs;
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                Item item = new Item(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("price"), rs.getInt("quantity"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(items);
        return items;
    }

    public void changePrice(int id, int price) {
        try {
            String query = "UPDATE items SET price=" + price + "WHERE id = " + id;
            updateDB(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDB(String query) {
        Logger.getInstance().log(LoggerType.DEBUG, "updateDB()");
        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "DashboardDatabaseManager : SQL Error " + e.getMessage());
        }
    }


}
