package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.awt.print.PrinterException;

public class ViewMentorsFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton deleteButton, printButton, backButton;

    public ViewMentorsFrame() {
        setTitle("View Mentors - Admin Dashboard");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(248, 248, 255));

        // Custom Header Label
        JLabel headerLabel = new JLabel("Mentors Data", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerLabel.setForeground(new Color(72, 61, 139));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Table customization
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(123, 104, 238));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 250));
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setBorder(new LineBorder(new Color(186, 85, 211), 1));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(186, 85, 211), 2));
        add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(248, 248, 255));

        deleteButton = createStyledButton("Delete", new Color(255, 99, 71));
        printButton = createStyledButton("Print", new Color(100, 149, 237));
        backButton = createStyledButton("Back", new Color(60, 179, 113));

        buttonPanel.add(deleteButton);
        buttonPanel.add(printButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load Data
        loadMentorData();

        // Actions
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this mentor?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                    deleteMentorById(id);
                    model.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row.");
            }
        });

        printButton.addActionListener(e -> {
            try {
                table.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Printing error: " + ex.getMessage());
            }
        });

        backButton.addActionListener(e -> this.dispose());
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(130, 40));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.WHITE, 2, true));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void loadMentorData() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/startup_db", "root", "Talha@101");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM mentor")) {

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
            JOptionPane.showMessageDialog(this, "Error loading mentors.");
        }
    }

    private void deleteMentorById(int id) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/startup_db", "root", "Talha@101");
             PreparedStatement ps = con.prepareStatement("DELETE FROM mentor WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Mentor deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Delete failed.");
        }
    }
}
