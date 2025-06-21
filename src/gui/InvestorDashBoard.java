package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class InvestorDashBoard extends JFrame {
    private JTable startupTable;
    private DefaultTableModel model;
    private JButton investButton, backButton;
    private JTextField amountField;
    private JLabel welcomeLabel;

    public InvestorDashBoard(String investorName) {
        setTitle("Investor Dashboard - Welcome " + investorName);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(40, 40, 80));
        headerPanel.setPreferredSize(new Dimension(900, 60));

        welcomeLabel = new JLabel("Welcome, Investor " + investorName);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(welcomeLabel);

        // Table Setup
        String[] columns = { "Startup ID", "Startup Name", "Founder Name", "Industry", "Model", "Growth (%)" };
        model = new DefaultTableModel(columns, 0);
        startupTable = new JTable(model);
        startupTable.setRowHeight(28);
        startupTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        startupTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane(startupTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Available Startups"));
        loadStartupDataFromDB();

        // Investment Panel
        JPanel investPanel = new JPanel(null);
        investPanel.setPreferredSize(new Dimension(900, 100));
        investPanel.setBackground(new Color(245, 245, 250));

        JLabel amountLabel = new JLabel("Enter Investment Amount:");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        amountLabel.setBounds(60, 20, 200, 25);
        investPanel.add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(250, 20, 160, 28);
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        investPanel.add(amountField);

        investButton = new JButton("Invest");
        investButton.setBounds(430, 20, 100, 30);
        investButton.setBackground(new Color(0, 123, 255));
        investButton.setForeground(Color.WHITE);
        investButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        investPanel.add(investButton);

        backButton = new JButton("Back");
        backButton.setBounds(540, 20, 100, 30);
        backButton.setBackground(new Color(200, 60, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        investPanel.add(backButton);

        // Button Actions
        investButton.addActionListener(e -> handleInvestment(investorName));
        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true); 
        });

        // Add components
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(investPanel, BorderLayout.SOUTH);
    }

    private void handleInvestment(String investorName) {
        int selectedRow = startupTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a startup to invest in.");
            return;
        }

        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter investment amount.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            int startupId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            // Record investment and update growth
            recordInvestmentInDB(startupId, investorName, amount);

            // Refresh table to show updated growth
            model.setRowCount(0);
            loadStartupDataFromDB();

            JOptionPane.showMessageDialog(this, "Invested $" + amount + " successfully!");
            amountField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }

    }

    private void recordInvestmentInDB(int startupId, String investorName, double amount) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // 1. Get current growth percentage
            String selectSql = "SELECT growth_percentage FROM startups WHERE id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, startupId);
            ResultSet rs = selectStmt.executeQuery();

            double currentGrowth = 0;
            if (rs.next()) {
                currentGrowth = rs.getDouble("growth_percentage");
            }

            // 2. Increase by 10%
            double updatedGrowth = currentGrowth + 10;

            // 3. Update growth in startups table
            String updateGrowthSql = "UPDATE startups SET growth_percentage = ? WHERE id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateGrowthSql);
            updateStmt.setDouble(1, updatedGrowth);
            updateStmt.setInt(2, startupId);
            updateStmt.executeUpdate();

            // 4. Record investment (startup_growth table)
            String insertInvestmentSql = "INSERT INTO startup_growth (startup_id, investor_name, amount) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE amount = amount + VALUES(amount)";
            PreparedStatement investStmt = conn.prepareStatement(insertInvestmentSql);
            investStmt.setInt(1, startupId);
            investStmt.setString(2, investorName);
            investStmt.setDouble(3, amount);
            investStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database update failed.");
            e.printStackTrace();
        }
    }

    private void loadStartupDataFromDB() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT id, startup_name, founder_name, industry, model, growth_percentage FROM startups";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("startup_name"),
                        rs.getString("founder_name"),
                        rs.getString("industry"),
                        rs.getString("model"),
                        rs.getDouble("growth_percentage")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data from database.");
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/startup_db";
            String user = "root";
            String pass = "Talha@101";
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvestorDashBoard("InvestorName").setVisible(true));
    }
}
