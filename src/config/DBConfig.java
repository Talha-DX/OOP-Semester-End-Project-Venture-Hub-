package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {
    public static final String URL = "jdbc:mysql://localhost:3306/startup_db";
    public static final String USER = "root";
    public static final String PASSWORD = "Talha@101"; 

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
