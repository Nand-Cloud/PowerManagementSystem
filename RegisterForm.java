import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class RegisterForm {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Register Form");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Using absolute layout for simplicity

       
        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("back1.png");

        // Custom panel to display background image
        JPanel registerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage() , 0, 0,1000, 1000, this);
            }
        };
        registerPanel.setLayout(null);
        registerPanel.setBounds(0, 0, 1000, 700);
        frame.add(registerPanel);

        JLabel regLabel = new JLabel("Register User");
        regLabel.setHorizontalAlignment(SwingConstants.CENTER);
        regLabel.setBounds(130, 110, 150, 30);
        regLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        regLabel.setForeground(Color.BLACK); // Change text color for better visibility
        registerPanel.add(regLabel);

        JTextField nameField = new JTextField("Your Name");
        nameField.setBounds(50, 180, 300, 30);
        nameField.setToolTipText("Your Name");
        nameField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        registerPanel.add(nameField);

        JTextField emailField = new JTextField("example@email.com");
        emailField.setBounds(50, 220, 300, 30);
        emailField.setToolTipText("Your Email");
        emailField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        registerPanel.add(emailField);

        JPasswordField passwordField = new JPasswordField("password");
        passwordField.setBounds(50, 260, 300, 30);
        passwordField.setToolTipText("Your Password");
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        registerPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(50, 300, 300, 30);
        registerButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        registerButton.setBackground(Color.PINK);
        registerButton.setForeground(Color.BLACK);
        registerPanel.add(registerButton);

        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(Color.CYAN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(Color.PINK);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields");
                } else {
                    try {
                        // Database connection details
                        String url = "jdbc:postgresql://localhost:5432/app_proj";
                        String dbUser = "postgres";
                        String dbPassword = "6575";

                        // Register the PostgreSQL driver
                        Class.forName("org.postgresql.Driver");

                        // Connect to the database
                        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
                            // Check if the email is already registered
                            String checkQuery = "SELECT COUNT(*) FROM public.user WHERE email = ?";
                            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                                checkStmt.setString(1, email);
                                ResultSet rs = checkStmt.executeQuery();
                                rs.next();
                                int count = rs.getInt(1);

                                if (count > 0) {
                                    JOptionPane.showMessageDialog(frame, "Email is already registered.");
                                } else {
                                    // Insert new user if email is not found
                                    String insertQuery = "INSERT INTO public.user (name, email, password) VALUES (?, ?, ?)";
                                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                                        insertStmt.setString(1, name);
                                        insertStmt.setString(2, email);
                                        insertStmt.setString(3, password);

                                        int rowsInserted = insertStmt.executeUpdate();
                                        if (rowsInserted > 0) {
                                            JOptionPane.showMessageDialog(frame, "Registration Successful!");
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                    }
                }
            }
        });

        frame.setVisible(true);
    }
}
