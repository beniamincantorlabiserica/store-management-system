package database;

import logger.Logger;
import logger.LoggerType;
import model.WorkingHours;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardDatabaseManager {
    public DashboardDatabaseManager() {}

    public WorkingHours getWorkingHours() {
        Logger.getInstance().log(LoggerType.DEBUG, "DashboardDatabaseManager -> getWorkingHours()");
        String query = "select value from preferences where preference = 'workingHours'";
        ResultSet rs = queryDB(query);
        try {
            if(!rs.next()) {
                return new WorkingHours();
            } else {
                return new WorkingHours(rs.getString("value"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setWorkingHours(String workingHours) {
        Logger.getInstance().log(LoggerType.DEBUG, "DashboardDatabaseManager -> setWorkingHours()");
        String query = "update preferences set value = '" + workingHours + "' where preference = 'workingHours'";
        updateDB(query);
    }

    private void updateDB(String query) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            connection.createStatement().executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "DashboardDatabaseManager : SQL Error " + e.getMessage());
        }
    }

    private ResultSet queryDB(String query) {
        ResultSet resultSet = null;
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            connection.close();
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "DashboardDatabaseManager : SQL Error " + e.getMessage());
        }
        return resultSet;
    }
}
