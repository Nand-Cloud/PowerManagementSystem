package f2;

import f3.CapacityApp;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BuildingSelectorApp extends JFrame {

    public BuildingSelectorApp() {
        setTitle("Choose Building to Operate");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Assuming you have a background image named "ba2.jpg" in the same directory
        JLabel background = new JLabel(new ImageIcon("ba2.jpg"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JLabel header = new JLabel("Choose Building to Operate", JLabel.CENTER);
        header.setForeground(Color.black);
        header.setFont(new Font("Times New Roman", Font.BOLD, 28)); // Slightly larger font
        background.add(header, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 20)); // Use GridLayout for better spacing
        buttonPanel.setOpaque(false);

        // Panel for Building 1
        JPanel building1Panel = createBuildingPanel("bu1.png", "Building 1", e -> goToPage1());
        // Panel for Building 2
        JPanel building2Panel = createBuildingPanel("bu2.png", "Building 2", e -> goToPage2());

        // Add building panels to the main button panel
        buttonPanel.add(building1Panel);
        buttonPanel.add(building2Panel);

        background.add(buttonPanel, BorderLayout.CENTER);
    }

    private JPanel createBuildingPanel(String imagePath, String buttonText, ActionListener actionListener) {
        JPanel buildingPanel = new JPanel();
        buildingPanel.setLayout(new BorderLayout());
        buildingPanel.setOpaque(false); // Make the panel transparent

        // Load the image
        ImageIcon imageIcon = new ImageIcon(imagePath);
        
        // Check if the image was loaded successfully
        if (imageIcon.getIconWidth() == -1) {
            System.out.println("Image not found: " + imagePath);
        }

        // Add the main image above the button
        JLabel imageLabel = new JLabel(resizeImage(imageIcon, 100, 100));

        // Create the button with text and a smaller icon
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Change font style
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204)); // Set button color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        button.addActionListener(actionListener);

        // Resize and add the icon to the button
        ImageIcon buttonIcon = resizeImage(imageIcon, 30, 30); // Resize icon for button
        button.setIcon(buttonIcon);
        button.setHorizontalTextPosition(SwingConstants.RIGHT); // Position text to the right of the icon

        buildingPanel.add(imageLabel, BorderLayout.NORTH);
        buildingPanel.add(button, BorderLayout.SOUTH);
        return buildingPanel;
    }

    private ImageIcon resizeImage(ImageIcon originalIcon, int width, int height) {
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void goToPage1() {
        EventQueue.invokeLater(() -> {
            CapacityApp t = new CapacityApp();
            t.setVisible(true);
            this.dispose();
        });
    }

    private void goToPage2() {
        JOptionPane.showMessageDialog(this, "Navigating to Building 2...");
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> {
            BuildingSelectorApp app = new BuildingSelectorApp();
            app.setVisible(true);
        });
    }
}
