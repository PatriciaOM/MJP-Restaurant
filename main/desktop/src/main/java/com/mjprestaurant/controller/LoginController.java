package com.mjprestaurant.controller;

import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjprestaurant.model.User;
import com.mjprestaurant.view.LoginFrame;

public class LoginController {
    //Variables de classe
    private LoginFrame login;
    private final String LOGIN_URL = "http://tu-servidor.com/api/login"; // URL server

    
    public LoginController(LoginFrame login) {
        this.login = login;
        initController();
    }

    private void initController() {
        login.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                login();
            }
        });
    }

    // Mètode que fa POST al server i rep la resposta: ENCARA NO OPERATIU
    private void login() {
        try {
            String username = login.getUsername();
            String password = login.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(login, "Has d'introduïr l'usuari i la contrasenya");
                return;
            }

            User userToSend = new User(username, password, false);

            //Fem servir una llibreria externa per poder enviar més fàcilment les dades de l'usuari
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(userToSend);

            URI uri = new URI(LOGIN_URL);
            URL url = uri.toURL(); 
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            //Aconseguir l'stream de sortida del context actual i enviem les dades
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            //Rebem la resposta
            if (conn.getResponseCode() == 200) {
                try (InputStream is = conn.getInputStream()) {
                    User responseUser = mapper.readValue(is, User.class);
                    JOptionPane.showMessageDialog(login,
                            "Login correcte. Es admin: " + responseUser.isAdmin());
                }
            } else if (conn.getResponseCode() == 401) {
                JOptionPane.showMessageDialog(login, "Usuari o contrasenya incorrecte");
            } else {
                JOptionPane.showMessageDialog(login, "Error del servidor: " + conn.getResponseCode());
            }

            conn.disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(login, "Error de connexió amb el servidor");
        }
    }

}
