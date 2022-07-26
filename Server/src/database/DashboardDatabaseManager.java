package database;

import logger.Logger;
import logger.LoggerType;
import model.WorkingHours;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO for the dashboard
 */
public class DashboardDatabaseManager {
    private final DBConnection connection;
    private WorkingHours workingHours;

    public DashboardDatabaseManager(DBConnection connection) {
        this.connection = connection;
        workingHours = null;
    }

    /**
     * @return an object containing the working hours now contained into the database
     */
    public WorkingHours getWorkingHours() {
        if (workingHours != null) {
            return workingHours;
        }
        Logger.getInstance().log(LoggerType.DEBUG, "DashboardDatabaseManager -> getWorkingHours()");
        String query = "SELECT value " +
                "FROM preferences " +
                "WHERE preference = 'workingHours'";
        ResultSet rs = connection.queryDB(query);
        try {
            rs.next();
            return new WorkingHours(rs.getString("value"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setWorkingHours(String workingHours) {
        Logger.getInstance().log(LoggerType.DEBUG, "DashboardDatabaseManager -> setWorkingHours()");
        String query = "UPDATE preferences " +
                "SET value = '" + workingHours + "' " +
                "WHERE preference = 'workingHours'";
        connection.updateDB(query);
        this.workingHours = null;
    }

    /**
     * Logs the reaching method in the server as DEBUG
     * Gets today's date and builds a query counting all distinct checkout ids
     * Runs the query and loads the result in variable checkouts as String
     * Logs the number retrieved from the database in the server as DEBUG
     * @return checkouts
     */
    public String getCheckoutsToday() {
        Logger.getInstance().log(LoggerType.DEBUG, "DashboardDatabaseManager -> getCheckoutsToday()");
        String query = "SELECT COUNT(DISTINCT id) AS checkouts FROM checkouts WHERE DATE(timestamp) = CURRENT_DATE";
        ResultSet rs = connection.queryDB(query);
        String checkouts;
        try {
            rs.next();
            checkouts = String.valueOf(rs.getInt("checkouts"));
            Logger.getInstance().log(LoggerType.DEBUG, "CHECKOUTS TODAY: " + checkouts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkouts;
    }

    /**
     * Logs the reaching method in the server as DEBUG
     * Gets today's date and builds a query summing up all the items sold today
     * Runs the query and loads the result in variable items as String
     * Logs the number retrieved from the database in the server as DEBUG
     * @return items
     */
    public String getItemsToday() {
        Logger.getInstance().log(LoggerType.DEBUG, "DashboardDatabaseManager -> getItemsToday()");
        String query = "SELECT SUM(checkouts.quantity) AS items FROM checkouts WHERE DATE(timestamp) = CURRENT_DATE";
        ResultSet rs = connection.queryDB(query);
        String items;
        try {
            rs.next();
            items = String.valueOf(rs.getInt("items"));
            Logger.getInstance().log(LoggerType.DEBUG, "ITEMS TODAY: " + items);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    /**
     * Logs the reaching method in the server as DEBUG
     * Gets today's date and builds a query summing up all the items' price sold today
     * Runs the query and loads the result in variable sales as String
     * Logs the number retrieved from the database in the server as DEBUG
     * @return sales
     */
    public String getSalesToday() {
        Logger.getInstance().log(LoggerType.DEBUG, "DashboardDatabaseManager -> getSalesToday()");
        String query = "SELECT SUM(ck.quantity * i.price) AS sales FROM checkouts ck JOIN items i ON i.id = ck.itemid WHERE DATE(timestamp) = CURRENT_DATE";
        ResultSet rs = connection.queryDB(query);
        String sales;
        try {
            rs.next();
            sales = String.valueOf(rs.getDouble("sales"));
            Logger.getInstance().log(LoggerType.DEBUG, "SALES TODAY: " + sales);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sales;
    }

    public String getCheckoutsThisMonth() {
        String query = "SELECT COUNT(DISTINCT id) AS checkouts FROM checkouts WHERE timestamp >= CURRENT_DATE - INTERVAL '30D'";
        ResultSet rs = connection.queryDB(query);
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
        String query = "SELECT SUM(checkouts.quantity) AS items FROM checkouts WHERE timestamp >= CURRENT_DATE - INTERVAL '30D'";
        ResultSet rs = connection.queryDB(query);
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
        String query = "SELECT SUM(ck.quantity * i.price) AS sales " +
                "FROM checkouts ck " +
                "JOIN items i " +
                "ON i.id = ck.itemid " +
                "WHERE timestamp >= CURRENT_DATE - INTERVAL '30D'";
        ResultSet rs = connection.queryDB(query);
        String sales;
        try {
            rs.next();
            sales = String.valueOf(rs.getDouble("sales"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sales;
    }
}
