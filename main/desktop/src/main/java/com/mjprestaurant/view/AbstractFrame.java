package com.mjprestaurant.view;

import java.awt.*;
import javax.swing.*;

import com.mjprestaurant.model.CustomComponents;

/**
 * Classe abstracta que crea els components bàsics per qualsevol tipus de pantalla
 * @author Patricia Oliva
 */
public abstract class AbstractFrame extends JFrame {

    String username;
    private JButton btnLogout;

    /**
     * Constructor principal que crea la pantalla amb el títol passat per paràmetre
     * @param title títol de la pantalla
     */
    public AbstractFrame(String title) {
        super(title);

        // Opcions per defecte
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/img/sushi.png"));
        setIconImage(icon.getImage());

        // Afegim la barra de menú
        createMenuBar();

        // Crida al mètode abstracte perquè cada filla afegeixi els seus components
        initComponents();

        setVisible(true);
    }

    /**
     * Mètode que crea la barra de menú compartida per totes les pantalles
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); // margen horizontal y vertical

        new CustomComponents().setCustomButton("Tanca la sessió");
        btnLogout = CustomComponents.getCustomButton();

        menuBar.add(btnLogout);
        setJMenuBar(menuBar);
    }



    /**
     * Retorna el botó del logout
     * @return botó del logout
     */
    public JButton getBtnLogout() {
        return btnLogout;
    }

    /**
     * Inicialitza el nom de l'usuari. Es fa servir com a títol de la pantalla
     * @param username nom de l'usuari
     */
    public void setUserame(String username){
        this.username = username;
    }

    /**
     * Retorna el nom de l'usuari. Es fa servir com a títol de la pantalla
     * @return nom de l'usuari
     */
    public String getUsername(){
        return username;
    }

    // Mètode abstracte que les classes filles han d'implementar
    protected abstract void initComponents();
}
