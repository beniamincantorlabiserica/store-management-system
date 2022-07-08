package Database;


import logger.Logger;
import logger.LoggerType;

import java.sql.Connection;
import java.sql.DriverManager;

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
        return connection;
    }
}
