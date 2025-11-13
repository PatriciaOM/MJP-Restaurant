package com.mjprestaurant.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.model.table.TableRestaurant;
import com.mjprestaurant.view.AdminFrame;
import com.mjprestaurant.view.LoginFrame;
import com.mjprestaurant.view.TableFrame;
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
            TableFrame tables;
            List<TableRestaurant> tableList = java.util.Collections.emptyList();

            try {
                // Intentar obtener las mesas del servidor
                List<TableRestaurant> loadedTables = TableController.getAllTables(token);

                if (loadedTables != null) {
                    tableList = loadedTables;
                } else {
                    System.out.println("No s'han trobat taules registrades (resposta buida del servidor).");
                }

            } catch (ControllerException e1) {
                // Si falla la connexió, simplement mostrem un missatge i obrim la finestra igualment
                e1.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(adminFrame,
                        "No s'han pogut carregar les taules.\nPots crear-ne de noves amb el botó 'Afegir'.",
                        "Advertència", javax.swing.JOptionPane.WARNING_MESSAGE);
            }

            // Crear e inicializar siempre la ventana, aunque no haya mesas
            tables = new TableFrame("Taules del restaurant", tableList);
            tables.initLogout(login);
            adminFrame.addChildFrame(tables);
            tables.setLocationRelativeTo(null);
            tables.setVisible(true);
            adminFrame.setVisible(false);

            // Crear el controlador correspondiente
            new TableController(tables, login, token);

            // Acció per tornar enrere
            tables.getBtnBack().addActionListener(ev -> {
                tables.dispose();
                adminFrame.setVisible(true);
            });

            // Si la llista està buida, mostrar informació (opcional)
            if (tableList.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(tables,
                        "Encara no hi ha cap taula registrada.\nFes clic a 'Afegir' per crear-ne una.",
                        "Informació", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
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
