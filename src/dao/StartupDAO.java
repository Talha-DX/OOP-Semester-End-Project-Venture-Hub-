package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import config.DBConfig;
import models.Startup;

public class StartupDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/startup_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Talha@101";

    public static boolean createStartup(String founderName, String startupName, String industry,
                                        String model, String description, String website,
                                        String email, String password) {
        String sql = "INSERT INTO startups (founder_name, startup_name, industry, model, description, website, email, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, founderName);
            stmt.setString(2, startupName);
            stmt.setString(3, industry);
            stmt.setString(4, model);
            stmt.setString(5, description);
            stmt.setString(6, website);
            stmt.setString(7, email);
            stmt.setString(8, password);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Startup[] getAllStartups() {
        String sql = "SELECT * FROM startups";
        ArrayList<Startup> startupList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Startup startup = new Startup();
                startup.setId(rs.getInt("id"));
                startup.setFounderName(rs.getString("founder_name"));
                startup.setStartupName(rs.getString("startup_name"));
                startup.setIndustry(rs.getString("industry"));
                startup.setModel(rs.getString("model"));
                startup.setDescription(rs.getString("description"));
                startup.setWebsite(rs.getString("website"));
                startup.setEmail(rs.getString("email"));
                startup.setPassword(rs.getString("password"));

                startupList.add(startup);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return startupList.toArray(new Startup[0]);
    }

public static int fetchStartupGrowth(int startupId) {
    int growth_percentage = 0;
    try (Connection conn = DBConfig.getConnection()) {
        PreparedStatement stmt = conn.prepareStatement("SELECT growth_percentage FROM startups WHERE id = ?");
        stmt.setInt(1, startupId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            growth_percentage = rs.getInt("growth_percentage");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return growth_percentage;
}

public static Startup getStartupByName(String startupName) {
    Startup startup = null;
    String query = "SELECT * FROM startups WHERE startup_name = ?";

    try (Connection conn = DBConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, startupName);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            startup = new Startup();
            startup.setId(rs.getInt("id"));
            startup.setFounderName(rs.getString("founder_name"));
            startup.setStartupName(rs.getString("startup_name"));
            startup.setIndustry(rs.getString("industry"));
            startup.setModel(rs.getString("model"));
            startup.setDescription(rs.getString("description"));
            startup.setWebsite(rs.getString("website"));
            startup.setEmail(rs.getString("email"));
            
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return startup;
}
}
