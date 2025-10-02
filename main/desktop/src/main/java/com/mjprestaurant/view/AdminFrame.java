package com.mjprestaurant.view;

import java.awt.*;
import javax.swing.JButton;

public class AdminFrame  extends AbstractFrame {

    public AdminFrame(String title) {
        super(title);
    }

    @Override
    protected void initComponents() {
        JButton buttonNorth = new JButton("Botón NORTH");
        JButton buttonCenter = new JButton("Botón CENTER");

        buttonNorth.setFont(new Font("Arial", Font.PLAIN, 24));
        buttonCenter.setFont(new Font("Arial", Font.PLAIN, 24));

        add(buttonNorth, BorderLayout.NORTH);
        add(buttonCenter, BorderLayout.CENTER);
    }
    
}
