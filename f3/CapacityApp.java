package f3;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class BackgroundPanel extends JPanel {
    public BackgroundPanel() {
        // Set the background color to Apple white
        setBackground(new Color(245, 245, 245)); // RGB values for Apple white
        setOpaque(true); // Ensure the panel is opaque to show the color
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Only solid color background, no image drawing
    }
}

public class CapacityApp extends JFrame {

    private static float currentCapacity;
    private static float minimumCapacity;
    private static float totalCapacity;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Capacity Input");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout and add panel
        BackgroundPanel panel = new BackgroundPanel();
        panel.setLayout(null); // Set layout to null to control component positions manually
        frame.add(panel);

        // Place all components on the panel
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        JLabel capacityLabel = new JLabel("Enter total capacity:");
        capacityLabel.setForeground(Color.BLACK);
        capacityLabel.setBounds(10, 20, 150, 25);
        panel.add(capacityLabel);

        JTextField capacityText = new JTextField(20);
        capacityText.setBounds(160, 20, 165, 25);
        panel.add(capacityText);

        JLabel totalCapacityLabel = new JLabel("Total Capacity:");
        totalCapacityLabel.setForeground(Color.BLACK);
        totalCapacityLabel.setBounds(10, 60, 150, 25);
        panel.add(totalCapacityLabel);

        JLabel totalCapacityResult = new JLabel("");
        totalCapacityResult.setForeground(Color.BLACK);
        totalCapacityResult.setBounds(160, 60, 165, 25);
        panel.add(totalCapacityResult);

        JLabel minCapacityLabel = new JLabel("Enter minimum capacity:");
        minCapacityLabel.setForeground(Color.BLACK);
        minCapacityLabel.setBounds(10, 100, 150, 25);
        panel.add(minCapacityLabel);

        JTextField minCapacityText = new JTextField(20);
        minCapacityText.setBounds(160, 100, 165, 25);
        panel.add(minCapacityText);

        JLabel currentCapacityLabel = new JLabel("Current Capacity:");
        currentCapacityLabel.setForeground(Color.BLACK);
        currentCapacityLabel.setBounds(10, 140, 150, 25);
        panel.add(currentCapacityLabel);

        JLabel currentCapacityResult = new JLabel("");
        currentCapacityResult.setForeground(Color.BLACK);
        currentCapacityResult.setBounds(160, 140, 165, 25);
        panel.add(currentCapacityResult);

        JButton calculateButton = new JButton("Submit");
        calculateButton.setBounds(170, 180, 150, 25);
        panel.add(calculateButton);

        createFloorPanel(panel, "Floor 1", 220, 1.5);
        createFloorPanel(panel, "Floor 2", 340, 1.5);
        createFloorPanel(panel, "Floor 3", 460, 1.5);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    totalCapacity = Integer.parseInt(capacityText.getText());
                    minimumCapacity = Float.parseFloat(minCapacityText.getText());

                    totalCapacityResult.setText(String.valueOf(totalCapacity));
                    currentCapacity = totalCapacity;
                    currentCapacityResult.setText(String.valueOf(currentCapacity));

                    resetAllDevices(panel);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter valid numbers for capacities.");
                }
            }
        });
    }

    private static void createFloorPanel(JPanel parentPanel, String floorName, int yOffset, double decrementValue) {
        JPanel floorPanel = new JPanel();
        floorPanel.setLayout(null);
        floorPanel.setBorder(BorderFactory.createTitledBorder(floorName));
        floorPanel.setBounds(10, yOffset, 500, 100);
        parentPanel.add(floorPanel);

        JButton button1 = new JButton("Device 1 Off");
        button1.setBounds(10, 30, 150, 25);
        button1.setBackground(Color.RED);
        floorPanel.add(button1);

        JButton button2 = new JButton("Device 2 Off");
        button2.setBounds(160, 30, 150, 25);
        button2.setBackground(Color.RED);
        floorPanel.add(button2);

        JButton button3 = new JButton("Device 3 Off");
        button3.setBounds(310, 30, 150, 25);
        button3.setBackground(Color.RED);
        floorPanel.add(button3);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleDevice(button1, decrementValue, "Device 1", parentPanel);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleDevice(button2, decrementValue, "Device 2", parentPanel);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleDevice(button3, decrementValue, "Device 3", parentPanel);
            }
        });
    }

    private static void toggleDevice(JButton button, double value, String deviceName, JPanel parentPanel) {
        boolean isOn = button.getText().endsWith("On");

        if (isOn) {
            currentCapacity += value;
            button.setText(deviceName + " Off");
            button.setBackground(Color.RED);
        } else {
            if (currentCapacity >= value) {
                currentCapacity -= value;
                button.setText(deviceName + " On");
                button.setBackground(Color.GREEN);
            } else {
                JOptionPane.showMessageDialog(null, "Current capacity cannot go below zero.");
                return;
            }
        }

        if (currentCapacity < minimumCapacity) {
            JOptionPane.showMessageDialog(null, "Capacity below minimum. Turning off all devices.");
            resetAllDevices(parentPanel);
            currentCapacity = totalCapacity;
            switchOnOneDeviceEachFloor(parentPanel);
        }

        updateCurrentCapacityLabel(parentPanel);
    }

    private static void updateCurrentCapacityLabel(JPanel parentPanel) {
        JLabel currentCapacityLabel = (JLabel) parentPanel.getComponent(7);
        currentCapacityLabel.setText(String.valueOf(currentCapacity));
    }

    private static void resetAllDevices(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel floorPanel = (JPanel) component;
                for (Component floorComponent : floorPanel.getComponents()) {
                    if (floorComponent instanceof JButton) {
                        JButton button = (JButton) floorComponent;
                        button.setText(button.getText().replace("On", "Off"));
                        button.setBackground(Color.RED);
                    }
                }
            }
        }
    }

    private static void switchOnOneDeviceEachFloor(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel floorPanel = (JPanel) component;
                for (Component floorComponent : floorPanel.getComponents()) {
                    if (floorComponent instanceof JButton) {
                        JButton button = (JButton) floorComponent;
                        if (button.getText().endsWith("Off")) {
                            button.setText(button.getText().replace("Off", "On"));
                            button.setBackground(Color.GREEN);
                            currentCapacity -= 1.5; // Decrement the capacity by the value of the device
                            break;
                        }
                    }
                }
            }
        }
        updateCurrentCapacityLabel(panel);
    }
}
