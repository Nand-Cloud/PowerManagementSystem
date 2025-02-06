package f1;
import f2.BuildingSelectorApp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

// Custom JPanel class for a background image
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        try {
            backgroundImage = new ImageIcon(fileName).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}

public class LoginForm {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/app_proj";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "6575"; 
    private static final String AUTH_QUERY = "SELECT * FROM public.user WHERE email = ? AND password = ?";

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Login Form");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); 

        // Custom JPanel with a background image
        BackgroundPanel panel = new BackgroundPanel("back1.png"); // Path to your background image
        panel.setBounds(0, 0, 1000, 700);
        panel.setLayout(null);
        frame.add(panel);

        // Adding an image above the welcome label
        ImageIcon logoIcon = new ImageIcon("n1.png"); // Specify the correct path to your image
        Image image = logoIcon.getImage(); // Get the original image
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Resize to 200x200
        logoIcon = new ImageIcon(scaledImage); // Convert back to ImageIcon

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(100, 10, 200, 200); // Set bounds to match the scaled size
        panel.add(logoLabel);

        JLabel welcomeLabel = new JLabel("Welcome to IntelliAC");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(100, 220, 200, 30); // Adjusted position to accommodate the logo
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.RED); 
        panel.add(welcomeLabel);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setBounds(150, 260, 100, 25); // Adjusted position
        loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        loginLabel.setForeground(Color.WHITE);
        panel.add(loginLabel);

        JTextField emailField = new JTextField("email@example.com"); // Set default text
        emailField.setBounds(100, 300, 200, 30); // Adjusted position
        emailField.setToolTipText("Enter your Email");
        emailField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        panel.add(emailField);

        JPasswordField passwordField = new JPasswordField("password"); // Set default text
        passwordField.setBounds(100, 340, 200, 30); // Adjusted position
        passwordField.setToolTipText("Enter your Password");
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 380, 200, 30); // Adjusted position
        loginButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        loginButton.setBackground(Color.PINK);
        loginButton.setForeground(Color.BLACK);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        panel.add(loginButton);

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Color.CYAN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(Color.PINK);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement preparedStatement = connection.prepareStatement(AUTH_QUERY)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            System.out.println("Login successful");

                            // Close the Login Form
                            frame.dispose();

                            // Open BuildingSelectorApp
                            new BuildingSelectorApp().setVisible(true); // Show BuildingSelectorApp
                        } else {
                            System.out.println("Invalid email or password");
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("Error occurred during database connection: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
