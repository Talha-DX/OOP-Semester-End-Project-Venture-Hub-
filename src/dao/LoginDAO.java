package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public static boolean validateLogin(String role, String name, String password) {
        String tableName = getTableName(role);
        if (tableName == null) return false;

        String query = "SELECT * FROM " + tableName + " WHERE name = ? AND password = ?";

        try (Connection conn = DBHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Login successful if record found

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getTableName(String role) {
        return switch (role.toLowerCase()) {
            case "admin" -> "admin";
            case "founder" -> "founder";
            case "investor" -> "investor";
            case "mentor" -> "mentor";
            default -> null;
        };
    }
}
