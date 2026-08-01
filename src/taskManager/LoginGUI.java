package taskManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Class GUI for login to to_do_list
 */
@SuppressWarnings("serial")
public class LoginGUI extends JFrame {

    /**
     * User database
     */
    private UserDB userDB;

    /**
     * Constructor for LoginGUI
     */
    public LoginGUI() {
        userDB = new UserDB();
        initializeGUI();
    }

    private void initializeGUI() {
        // Frame setup
        setTitle("Task Manager - Login");
        setSize(450, 400);
        getContentPane().setLayout(null); // Absolute layout
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to TO-DO LIST", JLabel.CENTER);
        titleLabel.setBounds(50, 20, 350, 30);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204)); // Subtle blue
        getContentPane().add(titleLabel);

        // Username Label and TextField
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(75, 83, 75, 25);
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        getContentPane().add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(160, 80, 200, 30);
        usernameField.setToolTipText("Enter your username");
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setBorder(createRoundedBorder());
        getContentPane().add(usernameField);

        // Password Label and PasswordField
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(75, 143, 75, 25);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        getContentPane().add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(160, 140, 200, 30);
        passwordField.setToolTipText("Enter your password");
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBorder(createRoundedBorder());
        getContentPane().add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Submit");
        loginButton.setBounds(50, 220, 140, 40);
        styleButtonWithBorder(loginButton, new Color(0, 153, 76)); // Green button
        getContentPane().add(loginButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(220, 220, 140, 40);
        styleButtonWithBorder(registerButton, new Color(0, 102, 204)); // Blue button
        getContentPane().add(registerButton);

        // Button Actions
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (userDB.validateUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new TaskManagerGUI(username); // Pass the username to load tasks
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterGUI(); // Open the Register GUI
            dispose();
        });

        // Show frame
        setVisible(true);
    }

    /**
     * Creates a rounded border for text fields
     */
    private Border createRoundedBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true), // Rounded outer border
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding inside
        );
    }

    /**
     * Styles a JButton with custom colors, borders, and font
     */
    private void styleButtonWithBorder(JButton button, Color backgroundColor) {
        // Create a gradient background (lighter color for the top, darker for the bottom)
        Color darkerColor = backgroundColor.darker();
        button.setBackground(backgroundColor);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(darkerColor, 2, true)); // Border matching darker color

        // Set font and text color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        // Set rounded corners with padding inside
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(darkerColor, 3, true), // Thicker border
                BorderFactory.createEmptyBorder(5, 15, 5, 15) // Padding for text
        ));
        
        // Add a smooth gradient effect on the background when hovered
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Apply a gradient effect based on the button's state
                if (button.getModel().isPressed()) {
                    g2.setPaint(new GradientPaint(0, 0, backgroundColor.brighter(), 0, c.getHeight(), darkerColor)); // Darker shade when pressed
                } else if (button.getModel().isRollover()) {
                    g2.setPaint(new GradientPaint(0, 0, backgroundColor.brighter(), 0, c.getHeight(), darkerColor.darker())); // Brighter shade on hover
                } else {
                    g2.setPaint(new GradientPaint(0, 0, backgroundColor, 0, c.getHeight(), darkerColor)); // Default gradient
                }

                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25); // Rounded corners
                super.paint(g, c);
            }
        });

        // Set a hand cursor on hover
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }



    /**
     * Main method for the class
     * @param args arguments for main
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);
    }
}
