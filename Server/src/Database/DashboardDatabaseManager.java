package Database;

import logger.Logger;
import logger.LoggerType;
import model.WorkingHours;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardDatabaseManager {

    public DashboardDatabaseManager() {

    }

    public WorkingHours getWorkingHours() {
        Connection connection = connect();
        String query = "select * from preferences where preference = 'workingHours'";
        try {
            connection.close();
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "DashboardDatabaseManager -> getWorkingHours() : SQL Error");
        }
        return null;
    }

    private Connection connect() {
        DBConnection dbConnection = new DBConnection();
        return dbConnection.getConnection();
    }
}
