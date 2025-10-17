package com.mjprestaurant.controller;

import java.awt.event.ActionEvent;
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
import com.mjprestaurant.view.WaiterFrame;
import com.mjprestaurant.view.AdminFrame;
import com.mjprestaurant.model.*;

/**
 * Controlador del Login de l'aplicació desktop
 * @author Patricia Oliva
 */
public class LoginController {
    //Variables de classe
    private LoginFrame login;
    private final String LOGIN_URL = "http://localhost:8080/login"; // URL server
    private AdminFrame admin;
    private WaiterFrame waiter;
    private LoginResponse responseUser;
    private LogoutController logoutController;

    /**
     * Constructor principal
     * @param login pantalla de login
     */
    public LoginController(LoginFrame login) {
        this.login = login;
        initController();
    }

    /**
     * Mètode init per iniciar els components
     */
    private void initController() {
        login.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    /**
     * Mètode que recull de la pantalla del login les credencials username i password,
     * fa POST al servidor i rep la resposta.
     * Segons el tipus de user que es logueja, crea un tipus de pantalla AdminFrame o WaiterFrame
     * Les pantalles s'inicien amb el nom de l'usuari que s'ha loguejat, amaguen la pantalla del login
     * i inicialitzen els components corresponents. També es genera l'ActionListener necessari pel Logout
     * En cas de login invàlid, es genera un avís amb JOptionPane, igual que si n'hi ha problema de connexió
     * amb el servidor
     */
    private void login() {
        try {
            String username = login.getUsername();
            String password = login.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(login, "Has d'introduïr l'usuari i la contrasenya");
                return;
            }

            User userToSend = new User(username, password);

            //Fem servir una llibreria externa per poder enviar més fàcilment les dades de l'usuari
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(userToSend);
            System.out.println(json);

            URI uri = new URI(LOGIN_URL);
            URL url = uri.toURL(); 
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            //Aconseguim l'stream de sortida del context actual i enviem les dades
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            //Rebem la resposta
            if (conn.getResponseCode() == 200) { //Login correcte
                try (InputStream is = conn.getInputStream()) {
                    responseUser = mapper.readValue(is, LoginResponse.class);
                    System.out.println(responseUser.role); 
                    if (responseUser.role.equals(UserRole.ADMIN.getRole())){ //Usuari administrador
                        login.setVisible(false);
                        admin = new AdminFrame(username);
                        admin.setLocationRelativeTo(null); 
                        admin.setVisible(true);

                        //Control de logout
                        admin.getBtnLogout().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                logoutController = new LogoutController(admin, login, LoginController.this);
                                logoutController.logout(responseUser.token);
                            }
                        });
                    } else if (responseUser.role.equals(UserRole.USER.getRole())){ // Usuari cambrer
                        login.setVisible(false);
                        waiter = new WaiterFrame(username);
                        waiter.setLocationRelativeTo(null);
                        waiter.setVisible(true);

                        //Control de logout
                        waiter.getBtnLogout().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                logoutController = new LogoutController(waiter, login, LoginController.this);
                                logoutController.logout(responseUser.token);
                            }
                        });
                    } else {
                        System.out.println("Error de login");
                    }
                }
            } else if (conn.getResponseCode() == 401) { //Login invàlid
                JOptionPane.showMessageDialog(login, "Usuari o contrasenya incorrecte");
            } else { //Error de connexió
                JOptionPane.showMessageDialog(login, "Error del servidor: " + conn.getResponseCode());
            }

            conn.disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(login, "Error de connexió amb el servidor");
        }
    }
    
    /**
     * Retorna la pantalla de l'admin
     * @return pantalla d'administrador
     */
    public AdminFrame getAdminFrame() { return admin; }
    /**
     * Retorna la pantalla del user (cambrer)
     * @return pantalla de cambrers
     */
    public WaiterFrame getWaiterFrame() { return waiter; }

    /**
     * Retorna la pantalla de login
     * @return pantalla login
     */
    public LoginFrame getLogin() {
        return login;
    }

    /**
     * Inicialitza la pantalla de login
     * @param login
     */
    public void setLogin(LoginFrame login) {
        this.login = login;
    }

    /**
     * Retorna la resposta de l'usuari
     * @return usuari de resposta
     */
    public LoginResponse getResponseUser() {
        return responseUser;
    }

    /**
     * Inicialitza l'usuari que rep la resposta
     * @param responseUser
     */
    public void setResponseUser(LoginResponse responseUser) {
        this.responseUser = responseUser;
    }

    /**
     * Retorna el controlador del logout
     * @return controlador de logout
     */
    public LogoutController getLogoutController() {
        return logoutController;
    }

    /**
     * Inicialitza el controlador de logout
     * @param logoutController
     */
    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

}
