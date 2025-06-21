package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import dao.LoginDAO;

public class LoginFrame extends JFrame {
    private JComboBox<String> roleComboBox;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton loginButton, signUpButton;

    public LoginFrame() {
        setTitle("Venture Hub - Login");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // --- Left Panel (Logo + Title) ---
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 0));
        leftPanel.setBackground(new Color(30, 30, 60));
        leftPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Icon
        try {
            ImageIcon icon = new ImageIcon("C:/Users/pc/Documents/JAVA Files/VentureHub Project/src/Images/project-launch.png");
            Image scaledIcon = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
            leftPanel.add(iconLabel, gbc);
        } catch (Exception e) {
            System.out.println("Error loading icon: " + e.getMessage());
        }

        // Title
        JLabel titleLabel = new JLabel("VENTURE HUB");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        leftPanel.add(titleLabel, gbc);

        // --- Right Panel (Form) ---
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(245, 245, 245));
        loginPanel.setLayout(null);

        JLabel loginTitle = new JLabel("LOGIN");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        loginTitle.setForeground(new Color(40, 40, 70));
        loginTitle.setBounds(180, 30, 300, 40);
        loginPanel.add(loginTitle);

        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setBounds(120, 100, 100, 20);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(roleLabel);

        roleComboBox = new JComboBox<>(new String[]{"Admin", "Founder", "Investor", "Mentor"});
        roleComboBox.setBounds(220, 95, 180, 30);
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(roleComboBox);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(120, 150, 100, 20);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(220, 145, 180, 30);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(nameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(120, 200, 100, 20);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(220, 195, 180, 30);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setEchoChar('•');
        loginPanel.add(passwordField);

        // Show Password Checkbox
        JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBounds(220, 230, 150, 20);
        showPasswordCheckBox.setBackground(new Color(245, 245, 245));
        showPasswordCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showPasswordCheckBox.addItemListener(e -> {
            passwordField.setEchoChar(showPasswordCheckBox.isSelected() ? (char) 0 : '•');
        });
        loginPanel.add(showPasswordCheckBox);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(200, 270, 85, 35);
        loginButton.setBackground(new Color(60, 130, 250));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(loginButton);

        // Sign Up Button
        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(300, 270, 100, 35);
        signUpButton.setBackground(new Color(70, 200, 150));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(signUpButton);

        // --- Login Action ---
        loginButton.addActionListener(e -> {
            String role = (String) roleComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            if (role == null || role.isEmpty() || name.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                boolean isValid = LoginDAO.validateLogin(role, name, pass);
                if (isValid) {
                    JOptionPane.showMessageDialog(this, "Login successful as " + role + "!");
                    this.dispose();

                    switch (role) {
                        case "Admin":
                            new AdminFrame(name).setVisible(true);
                            break;
                        case "Founder":
                            new FounderFrame(name).setVisible(true);
                            break;
                        case "Investor":
                            new InvestorDashBoard(name).setVisible(true);
                            break;
                        case "Mentor":
                            try {
                                new MentorFrame(name).setVisible(true);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "Unknown role selected.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- Sign Up Action ---
        signUpButton.addActionListener(e -> new SignUpFrame().setVisible(true));

        // Add panels
        add(leftPanel, BorderLayout.WEST);
        add(loginPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
