package database;

import model.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDatabaseManager {
    private final DBConnection connection;

    public InventoryDatabaseManager(DBConnection connection) {
        this.connection = connection;
    }

    /**
     * @return an ArrayList of items from database
     */
    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        try {
            String query = "Select * from items order by id";
            ResultSet rs = connection.queryDB(query);
            while (rs.next()) {
                Item item = new Item(rs.getLong("id"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("quantity"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(items);
        return items;
    }

    public void changePrice(Long id, Double price) {
        try {
            String query = "UPDATE items SET price=" + price + "WHERE id = " + id;
            connection.updateDB(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Item isItem(int id) {
        String query = "SELECT * FROM items WHERE id = " + id;
        ResultSet rs = connection.queryDB(query);
        try {
            if (!rs.next()) {
                return null;
            }
            return new Item(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateQuantity(int id, int quantity) {
        try {
            String query = "UPDATE items SET quantity = " + quantity + " WHERE id = " + id;
            connection.updateDB(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
