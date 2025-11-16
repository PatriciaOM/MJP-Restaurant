package com.mjprestaurant.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.mjprestaurant.model.ControllerException;
import com.mjprestaurant.model.dish.Dish;
import com.mjprestaurant.model.table.TableRestaurant;
import com.mjprestaurant.model.user.User;
import com.mjprestaurant.view.AdminFrame;
import com.mjprestaurant.view.DishFrame;
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
        adminFrame.getButtonDishes().addActionListener(this);
        adminFrame.initLogout(login);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String token = loginController.getResponseUser().getToken();

        if (source == adminFrame.getButtonTables()) {
            System.out.println("Botón 'Taules' pulsado");
            List<TableRestaurant> tableList = java.util.Collections.emptyList();

            try {
                // Intentar obtenir les taules del servidor
                List<TableRestaurant> loadedTables = TableController.getAllTables(token);

                if (loadedTables != null) {
                    tableList = loadedTables;
                } else {
                    System.out.println("No s'han trobat taules registrades (resposta buida del servidor).");
                }

            } catch (ControllerException e1) {
                e1.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(adminFrame,
                        "No s'han pogut carregar les taules.\nPots crear-ne de noves amb el botó 'Afegir'.",
                        "Advertència", javax.swing.JOptionPane.WARNING_MESSAGE);
            }

            // Crear el controlador primer
            TableFrame tables = new TableFrame("Taules del restaurant", tableList, null); // temporal
            TableController tableController = new TableController(tables, login, token);
            tables.setController(tableController); // injectar el controlador en el frame

            tables.initLogout(login);
            adminFrame.addChildFrame(tables);
            tables.setLocationRelativeTo(null);
            tables.setVisible(true);
            adminFrame.setVisible(false);

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
            try {
                List<User> workerList = WorkerController.getAllWorkers(token);

                // Crear frame i controlador
                WorkerFrame workers = new WorkerFrame("Treballadors", workerList);
                WorkerController workerController = new WorkerController(workers, login, token);
                workers.setController(workerController);

                workers.initLogout(login);
                adminFrame.addChildFrame(workers);
                workers.setLocationRelativeTo(null);
                workers.setVisible(true);
                adminFrame.setVisible(false);

                // Acció para tornar enrere
                workers.getBtnBack().addActionListener(ev -> {
                    workers.dispose();
                    adminFrame.setVisible(true);
                });

                // Informació si la llista està buida
                if (workerList.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(workers,
                            "Encara no hi ha cap treballador registrat.\nFes clic a 'Afegir' per crear-ne un.",
                            "Informació", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (ControllerException e1) {
                e1.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(adminFrame,
                        "No s'han pogut carregar els treballadors.",
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == adminFrame.getButtonDishes()){
            try {
                List<Dish> dishesList = DishController.getAllDishes(token);

                // Crear frame i controlador
                DishFrame dishes = new DishFrame("Plats", dishesList);
                DishController dishController = new DishController(dishes, login, token);
                dishes.setController(dishController);

                dishes.initLogout(login);
                adminFrame.addChildFrame(dishes);
                dishes.setLocationRelativeTo(null);
                dishes.setVisible(true);
                adminFrame.setVisible(false);

                // Acció per tornar enrere
                dishes.getBtnBack().addActionListener(ev -> {
                    dishes.dispose();
                    adminFrame.setVisible(true);
                });

                // Información si la lista está vacía
                if (dishesList.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(dishes,
                            "Encara no hi ha cap plat registrat.\nFes clic a 'Afegir' per crear-ne un.",
                            "Informació", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (ControllerException e1) {
                e1.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(adminFrame,
                        "No s'han pogut carregar els plats.",
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } 
    }

    /**
     * Retorna la pantalla d'administració
     * @return pantalla d'admin
     */
    public AdminFrame getAdminFrame() {
        return adminFrame;
    }

    /**
     * Inicialitza la pantalla d'admin
     * @param adminFrame pantalla d'administració
     */
    public void setAdminFrame(AdminFrame adminFrame) {
        this.adminFrame = adminFrame;
    }

    /**
     * Retorna la pantalla de login
     * @return pantalla de login
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
     * Retorna el controlador de login
     * @return controlador pel login
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * Inicialitza el controlador del login
     * @param loginController controlador del login
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }


}
