package Database;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {


    public Connection getConnection() {

        Connection connection = null;
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://abul.db.elephantsql.com:5432/qvmbhbpf", "qvmbhbpf", "j8EWXTHxrhH17YY2QXhvh12KFgE2vy9w");

            if (connection != null) {
                System.out.println("Connection established!!!");
            } else {
                System.out.println("Connection failed =c");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
