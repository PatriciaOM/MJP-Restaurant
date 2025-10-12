package com.mjprestaurant.view;

import java.awt.*;
import javax.swing.*;

import com.mjprestaurant.model.CustomComponents;

public abstract class AbstractFrame extends JFrame {

    String username;
    private JButton btnLogout;

    public AbstractFrame(String title) {
        super(title);

        // Opcions per defecte
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Afegim la barra de menú
        createMenuBar();

        // Crida al mètode abstracte perquè cada filla afegeixi els seus components
        initComponents();

        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        new CustomComponents().setCustomButton("Tanca la sessió");
        btnLogout = CustomComponents.getCustomButton();

        menuBar.add(btnLogout);
        setJMenuBar(menuBar);
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public void setUserame(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    // Mètode abstracte que les classes filles han d'implementar
    protected abstract void initComponents();
}
