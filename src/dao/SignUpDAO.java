package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.DBConfig;

public class SignUpDAO {

    public static boolean insertUser(String role, String name, String address, String email, String phone, String password) {
        String tableName = getTableNameByRole(role);
        if (tableName == null) return false;

        if (isEmailTaken(tableName, email)) {
            return false;
        }

        String insertQuery = "INSERT INTO " + tableName +
                " (name, address, email, phone_no, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertQuery)) {

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, password);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isEmailTaken(String tableName, String email) {
        String checkQuery = "SELECT * FROM " + tableName + " WHERE email = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(checkQuery)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private static String getTableNameByRole(String role) {
        switch (role.toLowerCase()) {
            case "founder":
                return "founder";
            case "mentor":
                return "mentor";
            case "investor":
                return "investor";
            default:
                return null;
        }
    }
}
