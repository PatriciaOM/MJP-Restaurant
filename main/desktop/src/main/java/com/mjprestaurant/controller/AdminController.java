package com.mjprestaurant.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mjprestaurant.model.WorkerMockData;
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

    /**
     * Constructor principal
     * @param adminFrame pantalla d'administrador
     * @param login pantalla de login de la que venim
     */
    public AdminController(AdminFrame adminFrame, LoginFrame login) {
        this.adminFrame = adminFrame;
        this.login = login;
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

        if (source == adminFrame.getButtonTables()) {
            System.out.println("Botón 'Taules' pulsado");
        } else if (source == adminFrame.getButtonWorkers()) {
            WorkerFrame workers = new WorkerFrame("Treballadors", WorkerMockData.getMockWorkers());
            workers.initLogout(login);
            adminFrame.addChildFrame(workers);
            workers.setLocationRelativeTo(null);
            workers.setVisible(true);
            adminFrame.setVisible(false);
            new WorkerController(workers, login);

            workers.getBtnBack().addActionListener(ev -> {
                workers.dispose(); // Cierra la ventana actual
                adminFrame.setVisible(true); // Muestra la anterior
            });
        }
    }
}
