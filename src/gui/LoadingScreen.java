package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoadingScreen extends JFrame {
    private JProgressBar progressBar;
    private JLabel percentageLabel;

    public LoadingScreen() {
        setTitle("Venture Hub");
        setSize(600, 400);
        setLayout(null);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 600, 400, 30, 30));
        getContentPane().setBackground(new Color(240, 248, 255));

        // Title with gradient effect
        JLabel titleLabel = new JLabel("Venture Hub", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(63, 81, 181), 
                                getWidth(), 0, new Color(0, 150, 136));
                g2.setPaint(gradient);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x, y);
            }
        };
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setBounds(0, 50, 600, 50);
        add(titleLabel);

        // Logo
        try {
            String path = "C:/Users/pc/Documents/JAVA Files/VentureHub Project/src/Images/company.png";
            ImageIcon logoIcon = new ImageIcon(path);
            Image logoImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
            logoLabel.setBounds(240, 120, 120, 120);
            add(logoLabel);
        } catch (Exception e) {
            System.out.println("Error loading logo: " + e.getMessage());
        }

        // Modern progress bar
        progressBar = new JProgressBar(0, 100) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2.setColor(new Color(230, 230, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Progress
                if (getValue() > 0) {
                    int width = (int) ((getWidth() - 4) * ((double) getValue() / getMaximum()));
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(63, 81, 181), 
                                    width, 0, new Color(0, 150, 136));
                    g2.setPaint(gradient);
                    g2.fillRoundRect(2, 2, width, getHeight() - 4, 8, 8);
                }
                
                g2.dispose();
            }
        };
        progressBar.setBounds(150, 270, 300, 20);
        progressBar.setBorder(BorderFactory.createEmptyBorder());
        progressBar.setStringPainted(false);
        add(progressBar);

        // Percentage label with animation effect
        percentageLabel = new JLabel("0%", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(63, 81, 181));
                super.paintComponent(g2);
            }
        };
        percentageLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        percentageLabel.setBounds(275, 300, 50, 25);
        add(percentageLabel);

        // Loading message
         JLabel footerLabel = new JLabel("Loading, please wait...", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerLabel.setForeground(Color.GRAY);
        footerLabel.setBounds(200, 340, 200, 20);
        add(footerLabel);
    }

    public void showSplashThenRun(Runnable nextStep) {
        setVisible(true);

        Timer timer = new Timer(30, null);
        final int[] progress = {0};

        timer.addActionListener(e -> {
            if (progress[0] <= 100) {
                progressBar.setValue(progress[0]);
                percentageLabel.setText(progress[0] + "%");
                progress[0]++;
            } else {
                timer.stop();
                setVisible(false);
                dispose();
                nextStep.run();
            }
        });

        timer.start();
    }
}