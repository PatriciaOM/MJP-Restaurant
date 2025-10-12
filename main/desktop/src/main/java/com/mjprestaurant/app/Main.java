package com.mjprestaurant.app;

import com.mjprestaurant.controller.LoginController;
import com.mjprestaurant.view.*;

/**
 * Classe main
 * @author Patricia Oliva
 */
public class Main {
    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
        new LoginController(loginFrame);
        loginFrame.setVisible(true);
        
    }
}