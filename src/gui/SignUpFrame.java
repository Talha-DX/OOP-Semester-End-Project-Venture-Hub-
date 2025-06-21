package gui;

import javax.swing.*;
import java.awt.*;
import dao.SignUpDAO;

public class SignUpFrame extends JFrame {

    private JComboBox<String> roleBox;
    private JTextField nameField, addressField, emailField, phoneField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;

    public SignUpFrame() {
        setTitle("Venture Hub - Sign Up");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // --- Left Panel ---
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 0));
        leftPanel.setBackground(new Color(30, 30, 60));
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 0, 10, 0);

        ImageIcon icon = new ImageIcon("C:/Users/pc/Documents/JAVA Files/VentureHub Project/src/Images/sign-up.png");
        Image scaledIcon = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
        leftPanel.add(iconLabel, gbc);

        JLabel appTitle = new JLabel("VENTURE HUB", SwingConstants.CENTER);
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        appTitle.setForeground(Color.WHITE);
        leftPanel.add(appTitle, gbc);

        // --- Right Panel ---
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(245, 245, 245));
        formPanel.setLayout(null);

        JLabel title = new JLabel("Create Your Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(180, 20, 250, 30);
        title.setForeground(new Color(40, 40, 70));
        formPanel.add(title);

        // Labels and fields
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        int xLabel = 100, xField = 220, width = 180, height = 28, y = 70, gap = 40;

        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setBounds(xLabel, y, 100, 20);
        roleLabel.setFont(labelFont);
        formPanel.add(roleLabel);

        roleBox = new JComboBox<>(new String[]{"Founder", "Mentor", "Investor"});
        roleBox.setBounds(xField, y, width, height);
        roleBox.setFont(labelFont);
        formPanel.add(roleBox);

        y += gap;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(xLabel, y, 100, 20);
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(xField, y, width, height);
        nameField.setFont(labelFont);
        formPanel.add(nameField);

        y += gap;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(xLabel, y, 100, 20);
        addressLabel.setFont(labelFont);
        formPanel.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(xField, y, width, height);
        addressField.setFont(labelFont);
        formPanel.add(addressField);

        y += gap;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(xLabel, y, 100, 20);
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(xField, y, width, height);
        emailField.setFont(labelFont);
        formPanel.add(emailField);

        y += gap;
        JLabel phoneLabel = new JLabel("Phone No:");
        phoneLabel.setBounds(xLabel, y, 100, 20);
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(xField, y, width, height);
        phoneField.setFont(labelFont);
        formPanel.add(phoneField);

        y += gap;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(xLabel, y, 100, 20);
        passLabel.setFont(labelFont);
        formPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(xField, y, width, height);
        passwordField.setFont(labelFont);
        passwordField.setEchoChar('•');
        formPanel.add(passwordField);

        // Show Password Checkbox
        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setBounds(xField, y + 30, 150, 20);
        showPasswordCheckbox.setBackground(new Color(245, 245, 245));
        showPasswordCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(showPasswordCheckbox);

        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('•'); // Hide password
            }
        });

        // Buttons
        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(xField - 10, y + 60, 90, 35);
        signupBtn.setBackground(new Color(60, 130, 250));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupBtn.setFocusPainted(false);
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(signupBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(xField + 90, y + 60, 90, 35);
        backBtn.setBackground(new Color(100, 100, 100));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(backBtn);

        // Add panels
        add(leftPanel, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);

        // Button Actions
        signupBtn.addActionListener(e -> {
            String role = (String) roleBox.getSelectedItem();
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Password length check
            if (password.length() < 8) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean success = SignUpDAO.insertUser(role, name, address, email, phone, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "Sign up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sign up failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUpFrame().setVisible(true));
    }
}
