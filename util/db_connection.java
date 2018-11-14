package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author jnsch
 */

 public class db_connection {

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
            System.out.println("Class Not Found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static void disconnect(){ 
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            System.out.println("Disconnected from database.");
        }
    }

 }