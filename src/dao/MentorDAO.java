package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Mentor;
import models.Startup;

public class MentorDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/startup_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Talha@101";

    // --- 1. Get all mentors from the database ---
    public static List<Mentor> getAllMentors() {
        List<Mentor> mentors = new ArrayList<>();
        String query = "SELECT * FROM mentors";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mentor mentor = new Mentor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone_no"),
                        rs.getString("password")
                );
                mentors.add(mentor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mentors;
    }

    // --- 2. Hire a mentor for a startup ---
    public static boolean hireMentor(int startupId, int mentorId) {
        String insert = "INSERT INTO mentor_startup (startup_id, mentor_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setInt(1, startupId);
            stmt.setInt(2, mentorId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- 3. Get all startups linked to a mentor ---
    public static List<Startup> getStartupsByMentorId(int mentorId) {
        List<Startup> startups = new ArrayList<>();
        String query = "SELECT s.* FROM startups s " +
                       "JOIN mentor_startup ms ON s.id = ms.startup_id " +
                       "WHERE ms.mentor_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, mentorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Startup startup = new Startup(
                        rs.getInt("id"),
                        rs.getString("founder_name"),
                        rs.getString("startup_name"),
                        rs.getString("industry"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getString("website"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                startups.add(startup);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return startups;
    }
}
