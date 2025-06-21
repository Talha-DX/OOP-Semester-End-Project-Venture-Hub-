package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import config.DBConfig;

import java.awt.*;
import java.sql.*;

public class MentorFrame extends JFrame {

    private JTable startupTable;
    private DefaultTableModel tableModel;
    private String mentorName;

    public MentorFrame(String mentorName) throws Exception {
        this.mentorName = mentorName;
        setTitle("Mentor Dashboard - Welcome " + mentorName);
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 250, 255));

        // Header
        JLabel headerLabel = new JLabel("Welcome, " + mentorName );
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(new Color(33, 111, 219));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"ID", "Startup Name", "Founder", "Industry", "Model"};
        tableModel = new DefaultTableModel(columnNames, 0);
        startupTable = new JTable(tableModel);
        startupTable.setRowHeight(25);
        startupTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        startupTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane(startupTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadStartupData();

        // Buttons panel
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(245, 250, 255));
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 10));

        JButton sendMsgBtn = createStyledButton("Send Message", new Color(46, 204, 113));
        JButton reviewBtn = createStyledButton("Review Startup", new Color(52, 152, 219));
        JButton logoutBtn = createStyledButton("Logout", new Color(231, 76, 60));

        // Actions
        sendMsgBtn.addActionListener(e -> sendMessage());
        reviewBtn.addActionListener(e -> {
            try {
                reviewStartup();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true); // redirect to login
        });

        btnPanel.add(sendMsgBtn);
        btnPanel.add(reviewBtn);
        btnPanel.add(logoutBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void loadStartupData() throws Exception {
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, startup_name, founder_name, industry, model FROM startups")) {

            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("startup_name"),
                        rs.getString("founder_name"),
                        rs.getString("industry"),
                        rs.getString("model")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading startup data.");
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        int selectedRow = startupTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a startup to send a message.");
            return;
        }

        String startupName = tableModel.getValueAt(selectedRow, 1).toString();
        String message = JOptionPane.showInputDialog(this, "Enter your message for " + startupName + ":");

        if (message != null && !message.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Message sent to " + startupName + ": " + message);
            // Optional: Store message in DB
        } else {
            JOptionPane.showMessageDialog(this, "Message not sent.");
        }
    }

    private void reviewStartup() throws Exception {
        int selectedRow = startupTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a startup to review.");
            return;
        }

        int startupId = (int) tableModel.getValueAt(selectedRow, 0);

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM startups WHERE id = ?")) {

            stmt.setInt(1, startupId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                StringBuilder details = new StringBuilder();
                details.append("🟢 Startup Name: ").append(rs.getString("startup_name")).append("\n");
                details.append("👤 Founder: ").append(rs.getString("founder_name")).append("\n");
                details.append("🏢 Industry: ").append(rs.getString("industry")).append("\n");
                details.append("💡 Model: ").append(rs.getString("model")).append("\n");
                details.append("📝 Description: ").append(rs.getString("description")).append("\n");
                details.append("🌐 Website: ").append(rs.getString("website")).append("\n");
                details.append("📧 Email: ").append(rs.getString("email")).append("\n");

                JOptionPane.showMessageDialog(this, details.toString(), "Startup Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Startup details not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(180, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                String mentorName = "John Doe";
                new MentorFrame(mentorName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
