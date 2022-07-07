package server.Database;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {


    public Connection getConnection() {

        Connection connection = null;
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Jg11331133^");

            if (connection != null) {
                System.out.println("Connection established!!!");
            } else {
                System.out.println("Connection failed =c");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
}
