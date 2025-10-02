package com.mjprestaurant.view;

import java.awt.*;
import javax.swing.*;

public abstract class AbstractFrame extends JFrame {

    public AbstractFrame(String title) {

        super(title);

        //Opcions per defecte
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Crida al mètode per a afegir components
        initComponents();

        setVisible(true);
    }

    // Método abstracto que las clases hijas deben implementar
    protected abstract void initComponents();

}
