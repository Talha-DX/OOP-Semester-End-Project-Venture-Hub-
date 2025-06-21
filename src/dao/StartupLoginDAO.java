package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConfig;

public class StartupLoginDAO {

    public static boolean validateLogin(String startupName, String password) {
        String query = "SELECT * FROM startups WHERE startup_name = ? AND password = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, startupName);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            return rs.next(); // Login success if record exists

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getFounderName(String startupName) {
        String query = "SELECT founder_name FROM startups WHERE startup_name = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, startupName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("founder_name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getStartupId(String startupName) {
    String query = "SELECT id FROM startups WHERE startup_name = ?";

    try (Connection conn = DBConfig.getConnection();
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setString(1, startupName);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return -1;
}

}
