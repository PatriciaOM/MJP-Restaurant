package com.mjprestaurant.view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.mjprestaurant.controller.LogoutController;
import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.model.CustomComponents;

import java.util.List;

/**
 * Classe abstracta que crea els components bàsics per qualsevol tipus de pantalla
 * @author Patricia Oliva
 */
public abstract class AbstractFrame extends JFrame {
    protected List<AbstractFrame> childFrames = new ArrayList<>();

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
    protected void createMenuBar() {
        CustomComponents customComponents = new CustomComponents();
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10)); // margen horizontal y vertical

        customComponents.setCustomButton("Tanca la sessió");
        btnLogout = customComponents.getCustomButton();

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

    /**
     * Inicialitza el logout
     * @param login pantalla de login
     */
    public void initLogout(LoginFrame login) {
        btnLogout.addActionListener(e -> {
            try {
                new LogoutController(this, login).logout();
            } catch (ControllerException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Mètode que va afegint totes les pantalles que es van creant com a filles, per tal de tancar-les totes si es necessari
     * @param frame pantalla
     */
    public void addChildFrame(AbstractFrame frame) {
        childFrames.add(frame);
    }

    /**
     * Mètode que tanca totes les pantalles filles de manera recursiva
     */
    public void closeAllFramesRecursively() {
        for (AbstractFrame frame : childFrames) {
            frame.closeAllFramesRecursively();
            frame.dispose();
        }
        childFrames.clear();
        this.dispose();
    }

    /**Mètode abstracte que les classes filles han d'implementar
     * 
     */
    protected abstract void initComponents();
}
