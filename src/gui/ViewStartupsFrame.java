package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.print.PrinterException;

public class ViewStartupsFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton deleteButton, printButton, backButton;

    public ViewStartupsFrame() {
        setTitle("View Startups - Admin Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 250, 255));

        // --- Header Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255));
        headerPanel.setPreferredSize(new Dimension(900, 70));
        JLabel title = new JLabel("Startups Data");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);

        // --- Table Setup ---
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(65, 105, 225));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 255), 1));

        // --- Buttons Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(245, 250, 255));

        deleteButton = createStyledButton("Delete", new Color(220, 20, 60));
        printButton = createStyledButton("Print", new Color(34, 139, 34));
        backButton = createStyledButton("Back", new Color(70, 130, 180));

        buttonPanel.add(deleteButton);
        buttonPanel.add(printButton);
        buttonPanel.add(backButton);

        // --- Add components to frame ---
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- Load Data ---
        loadStartupData();

        // --- Delete Action ---
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                    deleteStartupById(id);
                    model.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        });

        // --- Print Action ---
        printButton.addActionListener(e -> {
            try {
                table.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Printing error: " + ex.getMessage());
            }
        });

        // --- Back Action ---
        backButton.addActionListener(e -> dispose());
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 40));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                new EmptyBorder(8, 16, 8, 16)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadStartupData() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/startup_db", "root", "Talha@101");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM founder")) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();

            model.setRowCount(0);
            model.setColumnCount(0);
            for (int i = 1; i <= colCount; i++) {
                model.addColumn(rsmd.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[colCount];
                for (int i = 0; i < colCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading startup data.");
        }
    }

    private void deleteStartupById(int id) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/startup_db", "root", "Talha@101");
             PreparedStatement ps = con.prepareStatement("DELETE FROM founder WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting record.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewStartupsFrame().setVisible(true));
    }
}
