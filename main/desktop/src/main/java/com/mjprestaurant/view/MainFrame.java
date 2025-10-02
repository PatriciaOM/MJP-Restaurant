package com.mjprestaurant.view;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal contenedor con BoxLayout vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Labels de título y subtítulo
        JLabel titleLabel = new JLabel("Bienvenido a la Aplicación");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel subtitleLabel = new JLabel("Por favor, introduce tus credenciales");
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Panel de login con GridLayout 2x3
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        // JLabel iconUser = new JLabel(new ImageIcon("/assets/img/users.png"));
        // iconUser.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextField txtUser = new JTextField();
        txtUser.setPreferredSize(new Dimension(150, 25)); // altura fija

        // JLabel iconPass = new JLabel(new ImageIcon("/assets/img/lock.png"));
        // iconPass.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
        JPasswordField txtPass = new JPasswordField();
        txtPass.setPreferredSize(new Dimension(150, 25)); // altura fija

        // Añadir componentes al panel de login
        //loginPanel.add(iconUser);
        loginPanel.add(lblUser);
        loginPanel.add(txtUser);

        //loginPanel.add(iconPass);
        loginPanel.add(lblPass);
        loginPanel.add(txtPass);

        // Panel contenedor para centrar el loginPanel
        JPanel centerPanel = new JPanel();
        centerPanel.add(loginPanel);

        // Añadir todo al mainPanel
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(centerPanel);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // centra la ventana en la pantalla
        frame.setVisible(true);
    }
}
