package com.mjprestaurant.app;

import javax.swing.JOptionPane;

import com.mjprestaurant.controller.LoginController;
import com.mjprestaurant.controller.SSLConfig;
import com.mjprestaurant.view.*;

/**
 * Classe main
 * @author Patricia Oliva
 */
public class Main {
    public static void main(String[] args) {

        try {
            SSLConfig.init();   
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Error inicializando SSL:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        LoginFrame loginFrame = new LoginFrame();
        new LoginController(loginFrame);
        loginFrame.setVisible(true);
        
    }
}