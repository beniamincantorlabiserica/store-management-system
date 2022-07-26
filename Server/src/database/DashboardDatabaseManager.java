package database;

import logger.Logger;
import logger.LoggerType;
import model.WorkingHours;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        String query = "select value from preferences where preference = 'workingHours'";
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
        String query = "update preferences set value = '" + workingHours + "' where preference = 'workingHours'";
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String query = "Select count(distinct id) as checkouts from checkouts where timestamp='" + dtf.format(now) + "'";
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String query = "Select sum(checkouts.quantity) as items from checkouts where timestamp='" + dtf.format(now) + "'";
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String query = "SELECT SUM(ck.quantity * i.price) AS sales FROM checkouts ck JOIN items i ON i.id = ck.itemid WHERE timestamp='" + dtf.format(now) + "'";
        ResultSet rs = connection.queryDB(query);
        String sales;
        try {
            rs.next();
            sales = String.valueOf(rs.getInt("sales"));
            Logger.getInstance().log(LoggerType.DEBUG, "SALES TODAY: " + sales);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sales;
    }

    public String getCheckoutsThisMonth() {
        int year = LocalDateTime.now().getYear();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String nextMonth = String.valueOf(Integer.parseInt(month));
        String query = "SELECT COUNT(DISTINCT id) AS checkouts FROM checkouts WHERE timestamp >= CURRENT_DATE + INTERVAL '30 days'";
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
        int year = LocalDateTime.now().getYear();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String nextMonth = String.valueOf(Integer.parseInt(month));
        String query = "SELECT SUM(checkouts.quantity) as items from checkouts where '01/" + month + "/" + year + "' <= checkouts.timestamp and checkouts.timestamp < '01/" + nextMonth + "/" + year + "';";
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
        int year = LocalDateTime.now().getYear();
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM"));
        String nextMonth = String.valueOf(Integer.parseInt(month));
        String query = "SELECT SUM(ck.quantity * i.price) AS sales " +
                "FROM checkouts ck " +
                "JOIN items i " +
                "ON i.id = ck.itemid " +
                "WHERE '01/" + month + "/" + year + "' <= timestamp AND timestamp < '01/" + nextMonth + "/" + year + "';";
        ResultSet rs = connection.queryDB(query);
        String sales;
        try {
            rs.next();
            sales = String.valueOf(rs.getInt("sales"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sales;
    }
}
