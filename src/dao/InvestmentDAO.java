package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InvestmentDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/startup_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Talha@101";

    public static boolean investInStartup(int startupId, int investmentAmount) {
        int growthIncrease = 10;

        String insertGrowthSQL = "INSERT INTO startup_growth (startup_id, growth_increment, investment_amount) VALUES (?, ?, ?)";
        String updateStartupSQL = "UPDATE startups SET growth_percentage = growth_percentage + ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            conn.setAutoCommit(false); // Begin transaction

            try (
                PreparedStatement insertStmt = conn.prepareStatement(insertGrowthSQL);
                PreparedStatement updateStmt = conn.prepareStatement(updateStartupSQL)
            ) {
                // Insert into startup_growth table
                insertStmt.setInt(1, startupId);
                insertStmt.setInt(2, growthIncrease);
                insertStmt.setInt(3, investmentAmount);
                insertStmt.executeUpdate();

                // Update startups table growth
                updateStmt.setInt(1, growthIncrease);
                updateStmt.setInt(2, startupId);
                updateStmt.executeUpdate();

                conn.commit(); // Commit both queries
                return true;
            } catch (Exception ex) {
                conn.rollback(); // Rollback if any error
                ex.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
}
