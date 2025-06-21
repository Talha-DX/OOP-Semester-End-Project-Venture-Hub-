package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import models.LoggedInUser;
import models.Startup;
import config.DBConfig;
import dao.StartupDAO;
import java.awt.*;
import java.awt.print.PrinterException;
import java.sql.*;

public class StartupDashBoard extends JFrame {
    private int startupId;
    private String startupName;
    private String founderName;
    private String industry;
    private String model;
    private String description;
    private String website;
    private String email;

    private Connection conn;
    private JProgressBar growthBar;

    public StartupDashBoard(String startupName) {
        this.startupName = startupName;
        
        try {
            this.conn = DBConfig.getConnection();
            fetchStartupData();
            initializeUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Failed to initialize dashboard: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void initializeUI() throws SQLException {
        setTitle("Startup Dashboard - " + startupName);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));

        JLabel welcomeLabel = new JLabel(startupName);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setForeground(new Color(30, 30, 80));
        welcomeLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Create tabs
        tabbedPane.addTab("Overview", createOverviewTab());
        tabbedPane.addTab("Services", createServicesTab());
        tabbedPane.addTab("Team", createTeamTab());
        tabbedPane.addTab("Information", createInformationTab());
        tabbedPane.addTab("Settings", createSettingsTab());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createOverviewTab() throws SQLException {
        JPanel overviewPanel = new JPanel();
        overviewPanel.setLayout(new BoxLayout(overviewPanel, BoxLayout.Y_AXIS));
        overviewPanel.setBackground(Color.white);
        overviewPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        Startup startup = StartupDAO.getStartupByName(startupName);
        if (startup == null) {
            overviewPanel.add(new JLabel("Startup information not found"));
            return overviewPanel;
        }

        overviewPanel.add(createInfoLabel("Startup Name: " + startup.getStartupName()));
        overviewPanel.add(createInfoLabel("Founder: " + startup.getFounderName()));
        overviewPanel.add(createInfoLabel("Industry: " + startup.getIndustry()));
        overviewPanel.add(createInfoLabel("Model: " + startup.getModel()));
        overviewPanel.add(createInfoLabel("Description: " + startup.getDescription()));
        overviewPanel.add(createInfoLabel("Website: " + startup.getWebsite()));
        overviewPanel.add(createInfoLabel("Email: " + startup.getEmail()));

        overviewPanel.add(Box.createVerticalGlue());
        return overviewPanel;
    }

    private JPanel createServicesTab() {
        JPanel servicesPanel = new JPanel(new BorderLayout());
        servicesPanel.setBackground(Color.white);
        servicesPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] serviceColumns = {"Service Name", "Price", "Description"};
        DefaultTableModel serviceModel = new DefaultTableModel(serviceColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        JTable serviceTable = new JTable(serviceModel);
        serviceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane serviceScrollPane = new JScrollPane(serviceTable);

        loadTableData(serviceModel, "services", new String[]{"service_name", "price", "description"});
        servicesPanel.add(serviceScrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.white);

        JButton addServiceBtn = createStyledButton("Add Service", new Color(0, 120, 215));
        JButton deleteServiceBtn = createStyledButton("Delete Selected", new Color(220, 20, 60));
        JButton printServiceBtn = createStyledButton("Print Table", new Color(255, 165, 0));

        addServiceBtn.addActionListener(e -> showAddServiceDialog(serviceModel));
        deleteServiceBtn.addActionListener(e -> deleteSelectedRow(serviceTable, serviceModel, "services", "service_name"));
        printServiceBtn.addActionListener(e -> printTable(serviceTable));

        buttonPanel.add(addServiceBtn);
        buttonPanel.add(deleteServiceBtn);
        buttonPanel.add(printServiceBtn);

        servicesPanel.add(buttonPanel, BorderLayout.SOUTH);
        return servicesPanel;
    }

    private JPanel createTeamTab() {
        JPanel teamPanel = new JPanel(new BorderLayout());
        teamPanel.setBackground(Color.white);
        teamPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] teamColumns = {"Member Name", "Role", "Contact"};
        DefaultTableModel teamModel = new DefaultTableModel(teamColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable teamTable = new JTable(teamModel);
        teamTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane teamScrollPane = new JScrollPane(teamTable);

        loadTableData(teamModel, "team", new String[]{"member_name", "role", "contact"});
        teamPanel.add(teamScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.white);

        JButton addMemberBtn = createStyledButton("Add Member", new Color(0, 120, 215));
        JButton deleteMemberBtn = createStyledButton("Delete Selected", new Color(220, 20, 60));
        JButton printTeamBtn = createStyledButton("Print Table", new Color(255, 165, 0));

        addMemberBtn.addActionListener(e -> showAddTeamMemberDialog(teamModel));
        deleteMemberBtn.addActionListener(e -> deleteSelectedRow(teamTable, teamModel, "team", "member_name"));
        printTeamBtn.addActionListener(e -> printTable(teamTable));

        buttonPanel.add(addMemberBtn);
        buttonPanel.add(deleteMemberBtn);
        buttonPanel.add(printTeamBtn);

        teamPanel.add(buttonPanel, BorderLayout.SOUTH);
        return teamPanel;
    }

    private JPanel createInformationTab() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        infoPanel.setBackground(Color.white);

        JLabel title = new JLabel("Information");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel growthLabel = new JLabel("Growth of Startup");
        growthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        growthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize progress bar
        growthBar = new JProgressBar(0, 100);
        updateGrowthBar();
        growthBar.setStringPainted(true);
        growthBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(title);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        infoPanel.add(growthLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(growthBar);

        // Refresh button
        JButton refreshBtn = createStyledButton("Refresh Growth", new Color(50, 150, 50));
        refreshBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshBtn.addActionListener(e -> updateGrowthBar());
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(refreshBtn);

        return infoPanel;
    }

    private JPanel createSettingsTab() {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBackground(Color.white);
        settingsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton changePasswordBtn = createStyledButton("Change Password", new Color(0, 120, 215));
        changePasswordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePasswordBtn.addActionListener(e -> showChangePasswordDialog());

        JButton logoutBtn = createStyledButton("Logout", new Color(220, 20, 60));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.addActionListener(e -> {
            dispose();
            // Add any logout cleanup here
        });

        settingsPanel.add(Box.createVerticalGlue());
        settingsPanel.add(changePasswordBtn);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsPanel.add(logoutBtn);
        settingsPanel.add(Box.createVerticalGlue());

        return settingsPanel;
    }

    private void fetchStartupData() throws SQLException {
        String query = "SELECT id, founder_name, industry, model, description, website, email " +
                       "FROM startups WHERE startup_name = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.startupName);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                this.startupId = rs.getInt("id");
                this.founderName = rs.getString("founder_name");
                this.industry = rs.getString("industry");
                this.model = rs.getString("model");
                this.description = rs.getString("description");
                this.website = rs.getString("website");
                this.email = rs.getString("email");
            } else {
                throw new SQLException("No startup found with name: " + startupName);
            }
        }
    }

    private void loadTableData(DefaultTableModel model, String tableName, String[] columns) {
        try (PreparedStatement stmt = conn.prepareStatement(
                String.format("SELECT %s FROM %s WHERE startup_id = ?", 
                String.join(", ", columns), tableName))) {
            
            stmt.setInt(1, startupId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] row = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    row[i] = rs.getObject(columns[i]);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading " + tableName + " data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddServiceDialog(DefaultTableModel model) {
        JTextField nameField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField descField = new JTextField(20);

        Object[] inputs = {
            "Service Name:", nameField,
            "Price:", priceField,
            "Description:", descField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Add Service", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            if (nameField.getText().trim().isEmpty()) {
                showErrorDialog("Service name cannot be empty");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO services (startup_id, service_name, price, description) VALUES (?, ?, ?, ?)")) {
                
                stmt.setInt(1, startupId);
                stmt.setString(2, nameField.getText().trim());
                stmt.setString(3, priceField.getText().trim());
                stmt.setString(4, descField.getText().trim());
                stmt.executeUpdate();
                
                model.addRow(new Object[]{
                    nameField.getText().trim(),
                    priceField.getText().trim(),
                    descField.getText().trim()
                });
            } catch (SQLException e) {
                showErrorDialog("Failed to add service: " + e.getMessage());
            }
        }
    }

    private void showAddTeamMemberDialog(DefaultTableModel model) {
        JTextField nameField = new JTextField(20);
        JTextField roleField = new JTextField(20);
        JTextField contactField = new JTextField(20);

        Object[] inputs = {
            "Member Name:", nameField,
            "Role:", roleField,
            "Contact:", contactField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Add Team Member", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            if (nameField.getText().trim().isEmpty()) {
                showErrorDialog("Member name cannot be empty");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO team (startup_id, member_name, role, contact) VALUES (?, ?, ?, ?)")) {
                
                stmt.setInt(1, startupId);
                stmt.setString(2, nameField.getText().trim());
                stmt.setString(3, roleField.getText().trim());
                stmt.setString(4, contactField.getText().trim());
                stmt.executeUpdate();
                
                model.addRow(new Object[]{
                    nameField.getText().trim(),
                    roleField.getText().trim(),
                    contactField.getText().trim()
                });
            } catch (SQLException e) {
                showErrorDialog("Failed to add team member: " + e.getMessage());
            }
        }
    }

    private void deleteSelectedRow(JTable table, DefaultTableModel model, String tableName, String columnName) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showErrorDialog("Please select a row to delete");
            return;
        }

        String itemName = model.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete '" + itemName + "'?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM " + tableName + " WHERE " + columnName + " = ? AND startup_id = ?")) {
                
                stmt.setString(1, itemName);
                stmt.setInt(2, startupId);
                stmt.executeUpdate();
                model.removeRow(selectedRow);
            } catch (SQLException e) {
                showErrorDialog("Failed to delete: " + e.getMessage());
            }
        }
    }

    private void printTable(JTable table) {
        try {
            table.print();
        } catch (PrinterException e) {
            showErrorDialog("Failed to print: " + e.getMessage());
        }
    }

    private void updateGrowthBar() {
        try {
            int growth = StartupDAO.fetchStartupGrowth(startupId);
            growthBar.setValue(growth);
            growthBar.setString(growth + "%");
        } catch (Exception e) {
            showErrorDialog("Failed to update growth: " + e.getMessage());
        }
    }

    private void showChangePasswordDialog() {
        JPasswordField currentPassField = new JPasswordField(20);
        JPasswordField newPassField = new JPasswordField(20);
        JPasswordField confirmPassField = new JPasswordField(20);

        Object[] inputs = {
            "Current Password:", currentPassField,
            "New Password:", newPassField,
            "Confirm New Password:", confirmPassField
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Change Password", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            
            JOptionPane.showMessageDialog(this, "Password changed successfully");
        }
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        label.setForeground(new Color(50, 50, 50));
        label.setBorder(new EmptyBorder(5, 5, 5, 5));
        return label;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(140, 35));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void dispose() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.dispose();
    }
}