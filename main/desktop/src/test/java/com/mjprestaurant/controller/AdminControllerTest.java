package com.mjprestaurant.controller;

import com.mjprestaurant.model.LoginResponse;
import com.mjprestaurant.model.user.UserRole;
import com.mjprestaurant.view.AdminFrame;
import com.mjprestaurant.view.LoginFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

public class AdminControllerTest {

    private AdminFrame adminFrame;
    private LoginFrame loginFrame;
    private LoginController loginController;
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        adminFrame = new AdminFrame("Test");
        loginFrame = new LoginFrame();
        loginController = new LoginController(loginFrame);

        // Creamos el controlador
        adminController = new AdminController(adminFrame, loginFrame, loginController);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(adminFrame, adminController.getAdminFrame(), "El AdminFrame debería coincidir");
        assertEquals(loginFrame, adminController.getLogin(), "El LoginFrame debería coincidir");
        assertEquals(loginController, adminController.getLoginController(), "El LoginController debería coincidir");
    }

    @Test
    public void testSetters() {
        AdminFrame newAdminFrame = new AdminFrame("Test");
        LoginFrame newLoginFrame = new LoginFrame();
        LoginController newLoginController = new LoginController(newLoginFrame);

        adminController.setAdminFrame(newAdminFrame);
        adminController.setLogin(newLoginFrame);
        adminController.setLoginController(newLoginController);

        assertEquals(newAdminFrame, adminController.getAdminFrame());
        assertEquals(newLoginFrame, adminController.getLogin());
        assertEquals(newLoginController, adminController.getLoginController());
    }

    @Test
    public void testActionPerformedWithUnknownSource() {
        // Creamos un ActionEvent con un origen que no es ningún botón
    ActionEvent event = new ActionEvent(new Object(), ActionEvent.ACTION_PERFORMED, "test");

    // Inicializamos un LoginResponse ficticio
    loginController.setResponseUser(new LoginResponse("fake-token", UserRole.ADMIN));

    assertDoesNotThrow(() -> adminController.actionPerformed(event),
            "No debería lanzar excepción si el source no es ningún botón conocido");
    }

}
