package com.mjprestaurant.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.view.AdminFrame;
import com.mjprestaurant.view.LoginFrame;
import com.mjprestaurant.view.WorkerFrame;

/**
 * Classe controller de la pantalla admin
 * @author Patricia Oliva
 */
public class AdminController implements ActionListener {

    private AdminFrame adminFrame;
    private LoginFrame login;
    private LoginController loginController;

    /**
     * Constructor principal
     * @param adminFrame pantalla d'administrador
     * @param login pantalla de login de la que venim
     */
    public AdminController(AdminFrame adminFrame, LoginFrame login, LoginController loginController) {
        this.adminFrame = adminFrame;
        this.login = login;
        this.loginController = loginController;
        addListeners();
    }

    /**
     * Mètode per afegir els listeners
     */
    private void addListeners() {
        adminFrame.getButtonTables().addActionListener(this);
        adminFrame.getButtonWorkers().addActionListener(this);
        adminFrame.initLogout(login);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String token = loginController.getResponseUser().getToken();
        if (source == adminFrame.getButtonTables()) {
            System.out.println("Botón 'Taules' pulsado");
        } else if (source == adminFrame.getButtonWorkers()) {
            WorkerFrame workers;
            try {
                workers = new WorkerFrame("Treballadors", WorkerController.getAllWorkers(token));
                workers.initLogout(login);
                adminFrame.addChildFrame(workers);
                workers.setLocationRelativeTo(null);
                workers.setVisible(true);
                adminFrame.setVisible(false);
                new WorkerController(workers, login, token);

                workers.getBtnBack().addActionListener(ev -> {
                    workers.dispose(); // Cierra la ventana actual
                    adminFrame.setVisible(true); // Muestra la anterior
                });
            } catch (ControllerException e1) {
                e1.printStackTrace();
            }
        }
    }

    public AdminFrame getAdminFrame() {
        return adminFrame;
    }

    public void setAdminFrame(AdminFrame adminFrame) {
        this.adminFrame = adminFrame;
    }

    public LoginFrame getLogin() {
        return login;
    }

    public void setLogin(LoginFrame login) {
        this.login = login;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }


}
