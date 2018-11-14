package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author jnsch
 */

 public class db_connection {

    /**
     * Connection String
     * Server name:  52.206.157.109 
     * Database name:  U05Mc2
     * Username:  U05Mc2
     * Password:  53688542146
     */

    private static final String DB_NAME = "U05Mc2";
    private static final String USERNAME = "U05Mc2";
    private static final String PASSWORD = "53688542146";
    private static final String PATH = "jdbc:mysql://52.206.157.109/" + DB_NAME;
    private static final String DRIVER = "com.mysql.jdbc.Driver"; 

    private static Connection connection;

    public db_connection() {
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void connect() {
        System.out.print("\nConnecting to database... ");
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(PATH, USERNAME, PASSWORD);
            System.out.print("Connected\n");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(){ 
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Disconnected from database.");
        }
    }

 }