package database;


import logger.Logger;
import logger.LoggerType;

import java.sql.*;

public class DBConnection {
    private final Connection connection;

    public DBConnection() {
        this.connection = init();
    }

    public Connection init() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            //noinspection SpellCheckingInspection
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://abul.db.elephantsql.com:5432/qvmbhbpf",
                    "qvmbhbpf",
                    "j8EWXTHxrhH17YY2QXhvh12KFgE2vy9w"
            );
            if (connection != null) {
                Logger.getInstance().log("Connection established to DB");
            } else {
                Logger.getInstance().log(LoggerType.ERROR, "Connection to the database failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public ResultSet queryDB(String query) {
        ResultSet resultSet = null;
        try {
            Logger.getInstance().log(LoggerType.DEBUG, "queryDB() \nquery: " + query);
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "SQL Error " + e.getMessage() + "\nQuery: " + query);
        }
        return resultSet;
    }

    public void updateDB(String query) {
        Logger.getInstance().log(LoggerType.DEBUG, "updateDB() \nquery: " + query);
        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "SQL Error " + e.getMessage() + "\nQuery: " + query);
        }
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}
