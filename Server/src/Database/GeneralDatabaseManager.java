package Database;

import logger.Logger;
import logger.LoggerType;

import java.sql.Connection;
import java.sql.SQLException;

public class GeneralDatabaseManager {
    public void checkDB() {
        Logger.getInstance().log(LoggerType.DEBUG, "Error-checking DB..");
        String query = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, password VARCHAR(100), role VARCHAR(15));" +
                "CREATE TABLE IF NOT EXISTS items (id SERIAL PRIMARY KEY, name VARCHAR(50), quantity INT, price INT, priceControl BOOLEAN);" +
                "CREATE TABLE IF NOT EXISTS preferences (preference VARCHAR(20), value varchar(50));" +
                "CREATE TABLE IF NOT EXISTS checkouts (id INT, itemID INT REFERENCES items(id), quantity INT, price INT);" +
                "INSERT INTO preferences (preference, value) values ('workingHours', '09:00 17:00') ON CONFLICT DO NOTHING;" +
                "INSERT INTO users (password, role) VALUES ('gg77', 'cashier'), ('gg99', 'master') ON CONFLICT DO NOTHING;";
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            connection.createStatement().executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "DBConnection : SQL Error " + e.getMessage());
        }
    }
}
