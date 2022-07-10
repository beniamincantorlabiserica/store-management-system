package database;

import logger.Logger;
import logger.LoggerType;
import model.WorkingHours;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getCheckoutsThisMonth() {
        int year = LocalDateTime.now().getYear();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String query = "SELECT COUNT(DISTINCT id) as checkouts from checkouts where '" + year + "/" + month + "/01' <= checkouts.timestamp and checkouts.timestamp < '" + year + "/" + month+1 + "/01';";
        ResultSet rs = queryDB(query);
        String checkouts;
        try {
            rs.next();
            checkouts = String.valueOf(rs.getInt("checkouts"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkouts;
    }

    public String getItemsThisMonth() {
        int year = LocalDateTime.now().getYear();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String query = "SELECT SUM(checkouts.quantity) as items from checkouts where '" + year + "/" + month + "/01' <= checkouts.timestamp and checkouts.timestamp < '" + year + "/" + month+1 + "/01';";
        ResultSet rs = queryDB(query);
        String items;
        try {
            rs.next();
            items = String.valueOf(rs.getInt("items"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    public String getSalesThisMonth() {
        int year = LocalDateTime.now().getYear();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String query = "SELECT SUM(checkouts.price) as sales from checkouts where '" + year + "-" + month + "-01' <= checkouts.timestamp and checkouts.timestamp < '" + year + "-" + month+1 + "-01';";
        ResultSet rs = queryDB(query);
        String sales;
        try {
            rs.next();
            sales = String.valueOf(rs.getInt("sales"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sales;
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
