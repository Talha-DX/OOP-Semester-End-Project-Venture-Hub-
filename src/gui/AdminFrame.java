package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminFrame extends JFrame {
    private JLabel welcomeLabel;
    private JButton viewStartupsButton;
    private JButton viewMentorsButton;
    private JButton viewInvestorsButton;
    private JButton backButton;

        public AdminFrame(String adminName) {
        setTitle("Admin Dashboard - VentureHub");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(200, 220, 255), 0, getHeight(), new Color(150, 180, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        
        welcomeLabel = new JLabel("Welcome '" + adminName + "' to Venture Hub", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setForeground(new Color(10, 40, 80));
        welcomeLabel.setBounds(0, 30, 700, 40);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        backgroundPanel.add(welcomeLabel);

        
        JLabel shadowLabel = new JLabel("Welcome '" + adminName + "' to Venture Hub", SwingConstants.CENTER);
        shadowLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        shadowLabel.setForeground(new Color(0, 0, 0, 50));
        shadowLabel.setBounds(2, 32, 700, 40);
        backgroundPanel.add(shadowLabel);

        
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        centerPanel.setOpaque(false);
        centerPanel.setBounds(0, 100, 700, 200);

        viewStartupsButton = createStyledButton("View Startups", new Color(100, 150, 200));
        viewMentorsButton = createStyledButton("View Mentors", new Color(100, 150, 200));
        viewInvestorsButton = createStyledButton("View Investors", new Color(100, 150, 200));

        centerPanel.add(viewStartupsButton);
        centerPanel.add(viewMentorsButton);
        centerPanel.add(viewInvestorsButton);

        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBounds(0, 300, 700, 80);

        backButton = createStyledButton("Back", new Color(200, 80, 80));
        backButton.setPreferredSize(new Dimension(120, 40));
        bottomPanel.add(backButton);

        
        backgroundPanel.add(centerPanel);
        backgroundPanel.add(bottomPanel);

        
        viewStartupsButton.addActionListener(e -> new ViewStartupsFrame().setVisible(true));
        viewMentorsButton.addActionListener(e -> new ViewMentorsFrame().setVisible(true));
        viewInvestorsButton.addActionListener(e -> new ViewInvestorsFrame().setVisible(true));
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        button.setPreferredSize(new Dimension(160, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusable(false);

        
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Calculate hover and press colors
        Color hoverColor = baseColor.darker();
        Color pressColor = baseColor.darker().darker();

        // Hover and press effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
                button.setLocation(button.getX(), button.getY() - 2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
                button.setLocation(button.getX(), button.getY() + 2);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hoverColor);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminFrame frame = new AdminFrame("AdminName");
            frame.setVisible(true);
        });
    }
}