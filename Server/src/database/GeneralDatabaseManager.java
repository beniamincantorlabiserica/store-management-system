package database;

import logger.Logger;
import logger.LoggerType;

public class GeneralDatabaseManager {
    private final DBConnection connection;

    public GeneralDatabaseManager(DBConnection connection) {
        this.connection = connection;
    }

    public void checkDB() {
        Logger.getInstance().log(LoggerType.DEBUG, "Error-checking DB..");
        String query = "CREATE TABLE IF NOT EXISTS users (password VARCHAR(100), role VARCHAR(15) UNIQUE);" +
                "CREATE TABLE IF NOT EXISTS items (id SERIAL PRIMARY KEY, name VARCHAR(50), quantity INT, price DOUBLE PRECISION, priceControl BOOLEAN);" +
                "CREATE TABLE IF NOT EXISTS preferences (preference VARCHAR(20) UNIQUE, value varchar(50));" +
                "CREATE TABLE IF NOT EXISTS checkouts (id INT, itemID INT REFERENCES items(id), quantity INT, paymentMethod VARCHAR(15), timestamp timestamp);" +
                "INSERT INTO preferences (preference, value) values ('workingHours', '09:00 17:00'), ('lockedState', 'false') ON CONFLICT(preference) DO NOTHING;" +
                "INSERT INTO users (password, role) VALUES ('gg77', 'cashier'), ('gg99', 'storeManager'), ('gg57', 'master') ON CONFLICT(role) DO NOTHING;" +
                "ALTER TABLE checkouts ALTER COLUMN timestamp SET DEFAULT now();";
        Logger.getInstance().log(LoggerType.DEBUG, "checkDB() completed");
        connection.updateDB(query);
    }
}
