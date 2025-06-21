package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import config.DBConfig;

public class AdminDAO {

    // Delete a startup by ID
    public boolean deleteStartup(int startupId) throws Exception {
        String sql = "DELETE FROM startups WHERE id = ?";
        return executeDelete(sql, startupId);
    }

    // Delete a mentor by ID
    public boolean deleteMentor(int mentorId) throws Exception {
        String sql = "DELETE FROM mentors WHERE id = ?";
        return executeDelete(sql, mentorId);
    }

    // Delete an investor by ID
    public boolean deleteInvestor(int investorId) throws Exception {
        String sql = "DELETE FROM investors WHERE id = ?";
        return executeDelete(sql, investorId);
    }

    // Delete a founder by ID
    public boolean deleteFounder(int founderId) throws Exception {
        String sql = "DELETE FROM founders WHERE id = ?";
        return executeDelete(sql, founderId);
    }

    // Generic method to execute delete queries
    private boolean executeDelete(String sql, int id) throws Exception {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            // You can use your Logger class here to log the error
            return false;
        }
        
    }
}
