package com.mjprestaurant.view;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mjprestaurant.model.CustomComponents;

/**
 * Pantalla d'administradors
 * @author Patricia Oliva
 */
public class AdminFrame extends AbstractFrame {
    JButton buttonTables, buttonWorkers;
    /**
     * Constructor principal que seteja com a títol de la pantalla el nom de l'usuari loguejat
     * @param username nom de l'usuari
     */
    public AdminFrame(String username) {
        super(username);
    }

    /**
     * Mètode que sobreescriu el pare amb la inicialització dels components de la pantalla d'administradors
     */
    @Override
    public void initComponents() {
        CustomComponents customComponents = new CustomComponents();
        JLabel title = new JLabel("Administració de: ", SwingConstants.CENTER);
        System.out.println(title);
        title.setFont(new Font("Arial", Font.PLAIN, 24));
        
        customComponents.setCustomButton("Taules");
        buttonTables = customComponents.getCustomButton();
        customComponents.setCustomButton("Treballadors");
        buttonWorkers = customComponents.getCustomButton();
        
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

    /**
     * Mètode que retorna el botó de taules
     * @return botó taules
     */
    public JButton getButtonTables() {
        return buttonTables;
    }

    /**
     * Mètode que retorna el botó de treballadors
     * @return botó treballadors
     */
    public JButton getButtonWorkers() {
        return buttonWorkers;
    }
    
}
