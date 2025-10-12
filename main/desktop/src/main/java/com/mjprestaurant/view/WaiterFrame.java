package com.mjprestaurant.view;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class WaiterFrame extends AbstractFrame {

    public WaiterFrame(String username) {
        super(username);
    }

    @Override
    public void initComponents() {
        JLabel label = new JLabel("Pantalla Cambrers", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        add(label, BorderLayout.CENTER);
    }
    
}
