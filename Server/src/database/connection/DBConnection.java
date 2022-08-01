package database.connection;


import util.logger.Logger;
import util.logger.LoggerType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://abul.db.elephantsql.com:5432/qvmbhbpf";
    private static final String USER = "qvmbhbpf";
    private static final String PASSWORD = "j8EWXTHxrhH17YY2QXhvh12KFgE2vy9w";
    private Connection connection;

    public DBConnection() throws RuntimeException {
        init();
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                Logger.getInstance().log(LoggerType.DEBUG, "DB connection opened ->");
            } else {
                Logger.getInstance().log(LoggerType.ERROR, "Connection to the database failed.");
            }
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "Connection to the database failed.");
        }
    }

    public ResultSet queryDB(String query) {
        ResultSet resultSet = null;
        try {
            connect();
            Logger.getInstance().log(LoggerType.DEBUG, "queryDB() \nQuery: " + query);
            resultSet = connection.createStatement().executeQuery(query);
            close();
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "SQL Error: " + e.getMessage() + "\nQuery: " + query);
        }
        return resultSet;
    }

    public void updateDB(String query) {
        try {
            connect();
            Logger.getInstance().log(LoggerType.DEBUG, "updateDB() \nQuery: " + query);
            connection.createStatement().executeUpdate(query);
            close();
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "SQL Error: " + e.getMessage() + "\nQuery: " + query);
        }
    }

    private void close() throws SQLException {
        this.connection.close();
        Logger.getInstance().log(LoggerType.DEBUG, "<- DB connection closed");
    }

    public void init() {
        Logger.getInstance().log("Checking for required PostgreSQL driver..");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Logger.getInstance().log(LoggerType.ERROR, "PostgreSQL driver not found");
            throw new RuntimeException("POSTGRES_DRIVER_NOT_FOUND");
        }
        Logger.getInstance().log("PostgreSQL driver found");
        Logger.getInstance().log("Error-checking DB..");
        String query = "CREATE TABLE IF NOT EXISTS users (password VARCHAR(100), role VARCHAR(15) UNIQUE);" +
                "CREATE TABLE IF NOT EXISTS items (id SERIAL PRIMARY KEY, name VARCHAR(50), quantity INT, price DOUBLE PRECISION, priceControl BOOLEAN, lastedit timestamp);" +
                "CREATE TABLE IF NOT EXISTS preferences (preference VARCHAR(20) UNIQUE, value varchar(50));" +
                "CREATE TABLE IF NOT EXISTS checkouts (id INT, itemID INT REFERENCES items(id), quantity INT, paymentMethod VARCHAR(15), timestamp timestamp);" +
                "INSERT INTO preferences (preference, value) values ('closingTime', '17:00'), ('openingTime', '09:00'), ('lockedState', 'false') ON CONFLICT(preference) DO NOTHING;" +
                "INSERT INTO users (password, role) VALUES ('gg77', 'cashier'), ('gg99', 'storeManager'), ('gg57', 'master') ON CONFLICT(role) DO NOTHING;" +
                "ALTER TABLE checkouts ALTER COLUMN timestamp SET DEFAULT now();";
        updateDB(query);
        Logger.getInstance().log("DB error checking completed");
    }
}
