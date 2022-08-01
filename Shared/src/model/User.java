package model;

import util.logger.Logger;
import util.logger.LoggerType;

import java.io.Serializable;

public class User implements Serializable {
    private String role;
    private final String password;
    private final String masterPassword;

    public String getPassword() {
        return password;
    }

    public String getMasterPassword() {
        Logger.getInstance().log(LoggerType.DEBUG, "Expected master password: " + masterPassword);
        return masterPassword;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public boolean isMaster() {
        return role.equals("master");
    }

    public boolean isCashier() {
        return role.equals("cashier");
    }

    public boolean isStoreManager() {
        return role.equals("storeManager");
    }

    public User(String role, String password, String masterPassword) {
        this.role = role;
        this.password = password;
        this.masterPassword = masterPassword;
    }
}