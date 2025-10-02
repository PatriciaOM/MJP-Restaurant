package com.mjprestaurant.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class LoginFrame extends JFrame {
    //Variables de classe
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginFrame() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panell principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Labels pel títol i el subtítol
        JLabel titleLabel = new JLabel("Benvingut a MJPRestaurant");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel subtitleLabel = new JLabel("Introdueix el teu login: ");
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Panell de dades del login
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 10));

        //Usuari row: controlem errors a l'hora de no trobar els recursos
        URL userIconURL = getClass().getResource("/assets/img/users.png");
        ImageIcon iconUser = null;
        if (userIconURL != null) {
            Image original = new ImageIcon(userIconURL).getImage();
            Image scaled = original.getScaledInstance(25, 25, Image.SCALE_SMOOTH); // 20x20 píxeles
            iconUser = new ImageIcon(scaled);
        } else {
            System.err.println("No s'ha trobat users.png");
        }
        
        JLabel lblUser = new JLabel("Usuari:", iconUser, JLabel.LEFT);
        lblUser.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblUser.setIconTextGap(3); 
        lblUser.setHorizontalAlignment(SwingConstants.LEFT);
        txtUser = new JTextField();
        txtUser.setPreferredSize(new Dimension(150, 25)); // alçada fixe

        //Contrasenya row: controlem errors a l'hora de no trobar els recursos
        URL passIconURL = getClass().getResource("/assets/img/lock.png");   
        ImageIcon iconPass = null;

        if (passIconURL != null) {
            Image original = new ImageIcon(passIconURL).getImage();
            Image scaled = original.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            iconPass = new ImageIcon(scaled);
        } else {
            System.err.println("No s'ha trobat lock.png");
        }
       
        JLabel lblPass = new JLabel("Contrasenya:", iconPass, JLabel.LEFT);
        lblPass.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblPass.setIconTextGap(3); 
        lblPass.setHorizontalAlignment(SwingConstants.LEFT);
        txtPass = new JPasswordField();
        txtPass.setPreferredSize(new Dimension(150, 25)); // alçada fixe

        loginPanel.setBorder(BorderFactory.createEmptyBorder(0, 70, 0,0));

        // Afegim els components del login
        loginPanel.add(lblUser);
        loginPanel.add(txtUser);

        loginPanel.add(lblPass);
        loginPanel.add(txtPass);

        // Panel contenidor per poder centrar el GridLayout
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,100));
        containerPanel.add(loginPanel);

        JPanel buttonPanel = new JPanel(); // panel contenidor
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Botó custom de login
        btnLogin = new JButton("Entrar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fons i border
                g2.setColor(new Color(0, 128, 0)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            public boolean isContentAreaFilled() {
                // Es necessari per a que el fons del botó no es repinti amb les opcions per defecte
                return false;
            }

        };

        btnLogin.setBorder(BorderFactory.createEmptyBorder());

        // Preferencies del botó
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(100, 30));
        btnLogin.setMargin(new Insets(10, 32, 10, 32));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(btnLogin);

        // Afegim tot al contenidor principal
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 64)));
        mainPanel.add(containerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 64)));
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
        frame.pack(); //ajusta la finestra als components
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // centrem la finestra a la pantalla
        frame.setVisible(true);
    }

    public String getUsername() { return txtUser.getText(); }
    public String getPassword() { return new String(txtPass.getPassword()); }
    public JButton getBtnLogin() { return btnLogin; }

}
