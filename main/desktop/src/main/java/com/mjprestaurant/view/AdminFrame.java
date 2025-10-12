package com.mjprestaurant.view;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mjprestaurant.model.CustomComponents;

public class AdminFrame  extends AbstractFrame {

    public AdminFrame(String username) {
        super(username);
    }

    @Override
    public void initComponents() {
        JLabel title = new JLabel("Administració de: ", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        
        new CustomComponents().setCustomButton("Taules");
        JButton buttonTables = CustomComponents.getCustomButton();
        new CustomComponents().setCustomButton("Treballadors");
        JButton buttonWorkers = CustomComponents.getCustomButton();
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100,0,20,0));
        titlePanel.add(title, BorderLayout.CENTER);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
        adminPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        adminPanel.add(buttonTables);
        adminPanel.add(buttonWorkers);

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(adminPanel, BorderLayout.CENTER);

    }
    
}
