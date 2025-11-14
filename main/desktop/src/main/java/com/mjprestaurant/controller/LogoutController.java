package com.mjprestaurant.controller;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.view.*;

/**
 * Controlador del logout de l'aplicació desktop
 * @author Patricia Oliva
 */
public class LogoutController {

    private AbstractFrame userWindow;
    private LoginFrame login;

    public LogoutController(AbstractFrame userWindow, LoginFrame login) {
        this.userWindow = userWindow;
        this.login = login;
        initController();
    }

    private void initController() {
        userWindow.getBtnLogout().addActionListener(e -> {
            try {
                logout();
            } catch (ControllerException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void logout() throws ControllerException {
        try {
            String token = userWindow.getUsername(); // Se puede ajustar según tu token real
            HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/logout").openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.getOutputStream().write(("{\"token\":\"" + token + "\"}").getBytes());
            conn.getOutputStream().flush();

            if (conn.getResponseCode() == 200) {
                JOptionPane.showMessageDialog(null, "Sesión cerrada correctamente");
            }

            conn.disconnect();
            userWindow.closeAllFramesRecursively();
            userWindow.dispose();
            login.getTxtUser().setText("");
            login.getTxtPass().setText("");
            login.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error al desconectar", login);
        }
    }
}
