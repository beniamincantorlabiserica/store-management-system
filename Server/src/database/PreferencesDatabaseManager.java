package database;

import logger.Logger;
import logger.LoggerType;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PreferencesDatabaseManager {
    private final Connection connection;
    private String locked;
    public PreferencesDatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public void setLockedState(boolean b) throws RuntimeException {
        if (b == Boolean.parseBoolean(locked)) {
            Logger.getInstance().log(LoggerType.ERROR, "Trying to query the database with no change, cancelling..");
        }
        String query = "UPDATE preferences SET value = '" + b + "' WHERE preference = 'lockedState'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        locked = String.valueOf(b);
        if(b) {
            Logger.getInstance().log(LoggerType.WARNING, "The master password has been used to force lock the store!");
            return;
        }
        Logger.getInstance().log(LoggerType.WARNING, "The master password has been used to force unlock the store!");
    }

    public boolean getLockedState() {
        if (locked != null) {
            Logger.getInstance().log(LoggerType.DEBUG, "Local locked status exists, returning from cache as " + locked);
            return Boolean.parseBoolean(locked);
        }
        Logger.getInstance().log(LoggerType.DEBUG, "Local locked status null, fetching updated data..");
        String query = "SELECT value FROM preferences WHERE preference = 'lockedState'";
        Logger.getInstance().log(LoggerType.DEBUG,"getLockedState()");
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            locked = String.valueOf(rs.getBoolean("value"));
            Logger.getInstance().log(LoggerType.DEBUG, "Data fetched with status as " + locked);
            return Boolean.parseBoolean(locked);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
