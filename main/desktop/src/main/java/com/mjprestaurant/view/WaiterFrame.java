package com.mjprestaurant.view;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Pantalla de cambrers
 */
public class WaiterFrame extends AbstractFrame {

    /**
     * Constructor principal que seteja com a títol de la pantalla el nom de l'usuari loguejat
     * @param username nom de l'usuari
     */
    public WaiterFrame(String username) {
        super(username);
    }

    /**
     * Mètode que sobreescriu el pare amb la inicialització dels components de la pantalla de cambrers
     */
    @Override
    public void initComponents() {
        JLabel label = new JLabel("Pantalla Cambrers", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        add(label, BorderLayout.CENTER);
    }
    
}
