package database;

import model.Item;

import java.sql.Connection;
import java.sql.ResultSet;
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

}
