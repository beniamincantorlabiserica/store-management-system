package Database;


import logger.Logger;
import logger.LoggerType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://abul.db.elephantsql.com:5432/qvmbhbpf",
                    "qvmbhbpf",
                    "j8EWXTHxrhH17YY2QXhvh12KFgE2vy9w"
            );
            if (connection != null) {
                Logger.getInstance().log("Connection established");
            } else {
                Logger.getInstance().log(LoggerType.ERROR, "Connection to the database failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkDB();
        return connection;
    }

    private void checkDB() {
        String query = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, password VARCHAR(100), role VARCHAR(15));" +
                "CREATE TABLE IF NOT EXISTS checkouts (id INT, itemID INT REFERENCES item(id), quantity INT, price INT);" +
                "CREATE TABLE IF NOT EXISTS items (id SERIAL PRIMARY KEY, name VARCHAR(50), quantity INT, price INT, priceControl BOOLEAN);";
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
