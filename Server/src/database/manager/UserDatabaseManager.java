package database.manager;

import database.UserDAO;
import database.connection.DBConnection;
import model.User;
import util.logger.Logger;
import util.logger.LoggerType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabaseManager implements UserDAO {
    private final DBConnection connection;
    Statement statement = null;

    public UserDatabaseManager(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public User login(String password) {
        Logger.getInstance().log(LoggerType.DEBUG, "login()");
        ResultSet rs;
        String role = null;
        try {
            String query = "Select role from users where password= '" + password + "' limit 1";
            rs = connection.queryDB(query);
            if (rs.next()) {
                role = rs.getString("role");
            }
            if (role == null) {
                Logger.getInstance().log(LoggerType.WARNING, "Failed attempt to login with password " + password);
                return null;
            }
            Logger.getInstance().log("User logged in as " + role + ".");
            try {
                return new User(role, password, getMasterPassword());
            } catch (RuntimeException e) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * provides the master password from the database by querying it
     * @return the master password from the database as a string
     */
    @Override
    public String getMasterPassword() {
        Logger.getInstance().log(LoggerType.DEBUG,"getMasterPassword()");
        String masterPassword = null;
        try {
            String query = "select password from users where role='master'";
            ResultSet rs = connection.queryDB(query);
            if(rs.next()) {
                masterPassword = rs.getString("password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return masterPassword;
    }

    /**
     * updates the password in the database for the specified role
     * @param role the role for which the password to be updated
     * @param password the new password for the role specified
     * @throws RuntimeException throws exception in case the database communication failed or happened to be incomplete
     */
    @Override
    public void updatePassword(String role, String password) throws RuntimeException {
        Logger.getInstance().log(LoggerType.DEBUG, "Password: " + password + " Role: " + role);
        Logger.getInstance().log(LoggerType.DEBUG,"updatePassword()");
        ResultSet rs;
        try {
            String query = "Select * from users";
            rs = connection.queryDB(query);
            while (rs.next()) {
                String oldPassword = rs.getString("password");
                if (oldPassword.equals(password)) {
                    if (rs.getString("role").equals(role)) {
                        Logger.getInstance().log(LoggerType.ERROR, "The new password needs to be different");
                    } else {
                        Logger.getInstance().log(LoggerType.ERROR, "Passwords can not be the same for the store manager and the cashier");
                    }
                    throw new RuntimeException("PASSWORD_ALREADY_IN_USE");
                }
            }
            role = role.contains("Store") ? "storeManager" : "cashier";
            query = "update users set password='" + password
                    + "' where role='" + role + "'";
            connection.updateDB(query);
            Logger.getInstance().log(LoggerType.WARNING, "Password changed for " + role + " to " + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
