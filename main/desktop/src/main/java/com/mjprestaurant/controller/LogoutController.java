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

/**
 * Controlador del logout de l'aplicació desktop
 * @author Patricia Oliva
 */
public class LogoutController {
    private AbstractFrame userWindow;
    private LoginFrame login;
    private LoginController loginController;
    private final String LOGOUT_URL = "http://localhost:8080/logout"; // URL server

    /**
     * Constructor principal del logout, que necessita la finestra en la que es troba actualment l'usuari 
     * i la pantalla del login
     * @param userWindow pantalla en la que es troba l'usuari
     * @param login pantalla de login
     */
    public LogoutController(AbstractFrame userWindow, LoginFrame login, LoginController loginController) {
        this.login = login;
        this.userWindow = userWindow;
        this.loginController = loginController;
        initController();
    }

    /**
     * Mètode init per iniciar els components
     */
    private void initController() {
        userWindow.getBtnLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout(userWindow.getUsername());
            }
        });
    }

    /**
     * Mètode que fa el logout de l'aplicació desktop. Necessita el token de sessió per cancelar-la
     * Un cop s'ha rebut la resposta del servidor de la sessió tancada, genera un avís amb un JOptionPane
     * tanca la pantalla actual, posa la variable responseUser a null per permetre el login posterior d'un usuari
     * diferent, esborra els textos que hi havien al TextField i el PasswordField i torna a mostrar 
     * la pantalla de login
     * @param token token de sessió
     */
    public void logout(String token) {
        try {
            System.out.println(token);
            // Creem un objecte de tipus LogoutInfo que s'enviarà al server amb el token de sessió
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
            loginController.setResponseUser(null);
            login.getTxtUser().setText("");
            login.getTxtPass().setText("");
            login.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en desconnectar del servidor");
        }
    }

    /**
     * Retorna la pantalla en la que es troba l'usuari actualment
     * @return pantalla del user
     */
    public AbstractFrame getUserWindow() {
        return userWindow;
    }

    /**
     * Inicialitza la pantalla en la que es troba l'usuari
     * @param userWindow pantalla del user, de tipus AbstractFrame (pot ser admin o user)
     */
    public void setUserWindow(AbstractFrame userWindow) {
        this.userWindow = userWindow;
    }

    /**
     * Retorna la pantalla del login
     * @return pantalla de login
     */
    public LoginFrame getLogin() {
        return login;
    }

    /**
     * Inicialitza la pantalla del login
     * @param login pantalla de login
     */
    public void setLogin(LoginFrame login) {
        this.login = login;
    }

    
}
