package com.mjprestaurant.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjprestaurant.model.LogoutInfo;
import com.mjprestaurant.view.*;

public class LogoutController {
    private AbstractFrame userWindow;
    private LoginFrame login;
    private final String LOGOUT_URL = "http://localhost:8080/logout"; // URL server


    public LogoutController(AbstractFrame userWindow, LoginFrame login) {
        this.login = login;
        this.userWindow = userWindow;
        initController();
    }

    private void initController() {
        userWindow.getBtnLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout(userWindow.getUsername());
            }
        });
    }

    public void logout(String token) {
        try {
            System.out.println(token);
            // Crear objeto User para enviar al servidor
            LogoutInfo logoutinfo = new LogoutInfo(token);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(logoutinfo);

            URI uri = new URI(LOGOUT_URL);
            URL url = uri.toURL(); 
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                JOptionPane.showMessageDialog(null, "Sessió tancada correctament");
            } else {

                JOptionPane.showMessageDialog(null, "Error en tancar la sessió: " + conn.getResponseCode());
            }

            conn.disconnect();

            // Tornem al login
            if (userWindow != null) userWindow.dispose();

            login.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en desconnectar del servidor");
        }
    }
}
