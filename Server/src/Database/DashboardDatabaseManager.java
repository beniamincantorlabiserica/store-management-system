package Database;

import logger.Logger;
import logger.LoggerType;
import model.WorkingHours;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardDatabaseManager {

    public DashboardDatabaseManager() {

    }

    public WorkingHours getWorkingHours() {
        String query = "select value from preferences where preference = 'workingHours'";
        ResultSet rs = queryDB(query);
        try {
            if(!rs.next()) {
                String defaultHours = "09:00 17:00";
                query = "insert into preferences (preference, value) values ('workingHours', '" + defaultHours + "')";
                return new WorkingHours(defaultHours);
            } else {
                return new WorkingHours(rs.getString("value"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setWorkingHours(String workingHours) {
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
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            connection.close();
        } catch (SQLException e) {
            Logger.getInstance().log(LoggerType.ERROR, "DashboardDatabaseManager : SQL Error " + e.getMessage());
        }
        return resultSet;
    }
}
