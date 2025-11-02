package com.mjprestaurant.controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjprestaurant.view.LoginFrame;
import com.mjprestaurant.view.WaiterFrame;
import com.mjprestaurant.view.AdminFrame;
import com.mjprestaurant.model.*;

/**
 * Controlador del Login de l'aplicació desktop
 * @author Patricia Oliva
 */
public class LoginController {

    private LoginFrame login;
    private AdminFrame admin;
    private WaiterFrame waiter;
    private LoginResponse responseUser;

    /**
     * Constructor principal
     * @param login pantalla de login
     */
    public LoginController(LoginFrame login) {
        this.login = login;
        initController();
    }

    /**
     * Iniciador de components
     */
    private void initController() {
        login.getBtnLogin().addActionListener(e -> {
            try {
                login();
            } catch (ControllerException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Mètode que controla el funcionament del login
     * @throws ControllerException
     */
    public void login() throws ControllerException {
        try {
            String username = login.getUsername();
            String password = login.getPassword();
            if (username.isEmpty() || password.isEmpty())
                throw new ControllerException("Introduce usuario y contraseña", login);

            User userToSend = new User(username, password);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(userToSend);

            HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/login").openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                responseUser = mapper.readValue(conn.getInputStream(), LoginResponse.class);

                login.setVisible(false);
                if (responseUser.role.equals(UserRole.ADMIN.getRole())) {
                    admin = new AdminFrame(username);
                    admin.initLogout(login);
                    admin.setLocationRelativeTo(null);
                    admin.setVisible(true);

                    new AdminController(admin, login);

                } else if (responseUser.role.equals(UserRole.USER.getRole())) {
                    waiter = new WaiterFrame(username);
                    waiter.setLocationRelativeTo(null);
                    waiter.setVisible(true);

                    // Creamos LogoutController
                    new LogoutController(waiter, login);
                }

            } else if (conn.getResponseCode() == 401) {
                throw new ControllerException("Usuario o contraseña incorrectos", login);
            } else {
                throw new ControllerException("Error del servidor", login);
            }

            conn.disconnect();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ControllerException("Error de conexión", login);
        }
    }

    /**
     * Retorna la pantalla de login
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

    /**
     * Retorna la pantalla d'administradors
     * @return pantalla d'admin
     */
    public AdminFrame getAdmin() {
        return admin;
    }

    /**
     * Inicialitza pantalla d'administradors
     * @param admin pantalla d'admin
     */
    public void setAdmin(AdminFrame admin) {
        this.admin = admin;
    }

    /**
     * Retorna la pantalla de cambrers
     * @return pantalla de cambrers
     */
    public WaiterFrame getWaiter() {
        return waiter;
    }

    /**
     * Inicialitza la pantalla de cambrers
     * @param waiter pantalla de cambrers
     */
    public void setWaiter(WaiterFrame waiter) {
        this.waiter = waiter;
    }

    /**
     * Retorna el user introduït a la pantalla de login
     * @return user introduït
     */
    public LoginResponse getResponseUser() {
        return responseUser;
    }

    /**
     * Inicialitza el user de la pantalla de login
     * @param responseUser user
     */
    public void setResponseUser(LoginResponse responseUser) {
        this.responseUser = responseUser;
    }

    
}
