package com.mjprestaurant.model;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Classe que controla els errors de l'aplicació
 */
public class ControllerException extends Exception {
    
    /**
     * Constructor que mostra un JOptionPane amb el missatge d'error passat per paràmetre
     *
     * @param message Missatge d'error que mostrarà la pantalla
     * @param login Frame en el que apareixerà
     */
    public ControllerException(String message, JFrame frame) {
        super(message);
        JOptionPane.showMessageDialog(frame, message);
        return;
    }

    public ControllerException(String message) {
        super(message);
        System.out.println(message);
        return;
    }
}
