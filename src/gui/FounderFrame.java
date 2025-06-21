package gui;

import javax.swing.*;
import dao.StartupDAO;
import dao.StartupLoginDAO;
import models.LoggedInUser;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class FounderFrame extends JFrame {
    private JButton createStartupButton, loginStartupButton, logoutButton;

    public FounderFrame(String founderName) {
        setTitle("Founder Dashboard - VentureHub");
        setSize(700, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        // --- Top Panel ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome to VentureHub", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        logoutButton = new JButton("Logout");
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setPreferredSize(new Dimension(120, 40));
        topPanel.add(logoutButton, BorderLayout.EAST);

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        // --- Center Button Panel ---
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    URL imageUrl = new URL("https://images.unsplash.com/photo-1551288049-bebda4e38f71?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80");
                    BufferedImage image = ImageIO.read(imageUrl);
                    
                    Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    
                    // Apply opacity
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f)); // 20% opacity
                    g2d.drawImage(scaledImage, 0, 0, this);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Reset opacity
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.setLayout(new GridBagLayout());

        createStartupButton = new JButton("Create Startup");
        loginStartupButton = new JButton("Login to Startup");

        Dimension btnSize = new Dimension(200, 50);
        createStartupButton.setPreferredSize(btnSize);
        loginStartupButton.setPreferredSize(btnSize);

        createStartupButton.setBackground(new Color(0, 123, 255));
        createStartupButton.setForeground(Color.WHITE);
        createStartupButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        loginStartupButton.setBackground(new Color(40, 167, 69));
        loginStartupButton.setForeground(Color.WHITE);
        loginStartupButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(createStartupButton, gbc);

        gbc.gridx = 1;
        centerPanel.add(loginStartupButton, gbc);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        createStartupButton.addActionListener(e -> new CreateStartupFrame().setVisible(true));
        loginStartupButton.addActionListener(e -> new LoginStartupFrame().setVisible(true));
    }
    public static class CreateStartupFrame extends JFrame {
        public CreateStartupFrame() {
            setTitle("Create Your Startup");
            setSize(800, 700);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);

            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(new Color(245, 245, 245));
            mainPanel.setLayout(null);

            JLabel titleLabel = new JLabel("Create Your Startup");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
            titleLabel.setForeground(new Color(30, 30, 80));
            titleLabel.setBounds(240, 20, 400, 40);
            mainPanel.add(titleLabel);

            String[] labels = {
                    "Founder Name", "Startup Name", "Industry", "Business Model",
                    "Description", "Website", "Email", "Password"
            };

            JComponent[] inputs = new JComponent[labels.length];
            int y = 80;

            for (int i = 0; i < labels.length; i++) {
                JLabel label = new JLabel(labels[i] + ":");
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setBounds(80, y, 140, 25);
                mainPanel.add(label);

                if (labels[i].equals("Description")) {
                    JTextArea area = new JTextArea();
                    area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    area.setLineWrap(true);
                    JScrollPane scrollPane = new JScrollPane(area);
                    scrollPane.setBounds(230, y, 450, 60);
                    mainPanel.add(scrollPane);
                    inputs[i] = area;
                    y += 70;
                } else if (labels[i].equals("Password")) {
                    JPasswordField passwordField = new JPasswordField();
                    passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    passwordField.setBounds(230, y, 450, 30);
                    mainPanel.add(passwordField);
                    inputs[i] = passwordField;
                    y += 45;
                } else {
                    JTextField textField = new JTextField();
                    textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    textField.setBounds(230, y, 450, 30);
                    mainPanel.add(textField);
                    inputs[i] = textField;
                    y += 45;
                }
            }

            JButton createButton = new JButton("Create Startup");
            createButton.setBounds(230, y + 10, 160, 40);
            createButton.setBackground(new Color(0, 120, 215));
            createButton.setForeground(Color.WHITE);
            createButton.setFocusPainted(false);
            createButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            mainPanel.add(createButton);

            JButton clearButton = new JButton("Clear Form");
            clearButton.setBounds(420, y + 10, 140, 40);
            clearButton.setBackground(new Color(255, 120, 80));
            clearButton.setForeground(Color.WHITE);
            clearButton.setFocusPainted(false);
            clearButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            mainPanel.add(clearButton);

            JButton backButton = new JButton("Back");
            backButton.setBounds(600, y + 10, 80, 40);
            backButton.setBackground(Color.GRAY);
            backButton.setForeground(Color.WHITE);
            backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            mainPanel.add(backButton);

            backButton.addActionListener(e -> dispose());

            clearButton.addActionListener(e -> {
                for (JComponent field : inputs) {
                    if (field instanceof JTextField)
                        ((JTextField) field).setText("");
                    if (field instanceof JTextArea)
                        ((JTextArea) field).setText("");
                    if (field instanceof JPasswordField)
                        ((JPasswordField) field).setText("");
                }
            });

            createButton.addActionListener(e -> {
                try {
                    String founderName = ((JTextField) inputs[0]).getText().trim();
                    String startupName = ((JTextField) inputs[1]).getText().trim();
                    String industry = ((JTextField) inputs[2]).getText().trim();
                    String model = ((JTextField) inputs[3]).getText().trim();
                    String description = ((JTextArea) inputs[4]).getText().trim();
                    String website = ((JTextField) inputs[5]).getText().trim();
                    String email = ((JTextField) inputs[6]).getText().trim();
                    String password = new String(((JPasswordField) inputs[7]).getPassword()).trim();

                    if (founderName.isEmpty() || startupName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    boolean success = StartupDAO.createStartup(founderName, startupName, industry, model, description,
                            website, email, password);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Startup created successfully!");
                        for (JComponent field : inputs) {
                            if (field instanceof JTextField)
                                ((JTextField) field).setText("");
                            if (field instanceof JTextArea)
                                ((JTextArea) field).setText("");
                            if (field instanceof JPasswordField)
                                ((JPasswordField) field).setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Failed to create startup. Please check your input or try again later.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            add(mainPanel);
        }
    }

    public static class LoginStartupFrame extends JFrame {
        public LoginStartupFrame() {
            setTitle("Startup Login");
            setSize(450, 350);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(false);

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(new Color(245, 245, 245));

            JLabel titleLabel = new JLabel("Login to Your Startup");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
            titleLabel.setForeground(new Color(30, 30, 80));
            titleLabel.setBounds(100, 30, 300, 30);
            panel.add(titleLabel);

            JLabel startupNameLabel = new JLabel("Startup Name:");
            startupNameLabel.setBounds(50, 90, 120, 25);
            startupNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panel.add(startupNameLabel);

            JTextField startupNameField = new JTextField();
            startupNameField.setBounds(170, 90, 200, 30);
            startupNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panel.add(startupNameField);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(50, 140, 120, 25);
            passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panel.add(passwordLabel);

            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(170, 140, 200, 30);
            passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panel.add(passwordField);

            JButton loginButton = new JButton("Login");
            loginButton.setBounds(170, 200, 100, 40);
            loginButton.setBackground(new Color(0, 120, 215));
            loginButton.setForeground(Color.WHITE);
            loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            panel.add(loginButton);

            JButton backButton = new JButton("Back");
            backButton.setBounds(280, 200, 90, 40);
            backButton.setBackground(Color.GRAY);
            backButton.setForeground(Color.WHITE);
            backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panel.add(backButton);

            backButton.addActionListener(e -> dispose());

            loginButton.addActionListener(e -> {
                String startupName = startupNameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (startupName.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter both Startup Name and Password.",
                            "Missing Fields", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (StartupLoginDAO.validateLogin(startupName, password)) {
                    
                    int id = StartupLoginDAO.getStartupId(startupName);
                    LoggedInUser.setStartupId(id);

                    String founderName = StartupLoginDAO.getFounderName(startupName);
                    JOptionPane.showMessageDialog(this, "Login Successful! Founder: " + founderName);

                
                    try {
                        new StartupDashBoard(startupName).setVisible(true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Startup Name or Password.", "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            add(panel);
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new FounderFrame("FounderName").setVisible(true));
    }
}
