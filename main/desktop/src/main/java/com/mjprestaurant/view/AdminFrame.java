package com.mjprestaurant.view;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.mjprestaurant.model.CustomComponents;

public class AdminFrame  extends AbstractFrame {

    public AdminFrame(String title) {
        super(title);
    }

    @Override
    public void initComponents() {
        JButton buttonNorth = new JButton("Botón NORTH");
        new CustomComponents().setCustomButton("Administració de treballadors");
        JButton buttonCenter = CustomComponents.getCustomButton();

        JPanel adminPanel = new JPanel();

        buttonNorth.setFont(new Font("Arial", Font.PLAIN, 24));
        adminPanel.add(buttonCenter);

        add(buttonNorth, BorderLayout.NORTH);
        add(adminPanel, BorderLayout.CENTER);

    }
    
}
