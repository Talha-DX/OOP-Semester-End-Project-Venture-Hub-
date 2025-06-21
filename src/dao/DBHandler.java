package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import config.DBConfig;

public class DBHandler {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
    }
}
