package com.mjprestaurant.controller;
import com.mjprestaurant.model.*;
import com.mjprestaurant.view.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de proves per a LoginController.
 * 
 * Aquestes proves verifiquen el comportament intern de LoginController sense
 * utilitzar la interfície gràfica ni connexions de xarxa.
 * 
 * Tipus de proves: unitàries.
 * Eina utilitzada: JUnit 5.
 * @author Patricia Oliva
 */
class LoginControllerTest {

    private LoginTest loginTest;
    private LoginController controller;

    /**
     * Clase interna de prova que simula LoginFrame
     */
    static class LoginTest extends LoginFrame {
        private String username = "";
        private String password = "";

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public String getPassword() {
            return password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }

    /**
     * Abans de cada prova es crea un nou LoginTest i LoginController.
     * Això garanteix que cada test és independent.
     */
    @BeforeEach
    void setUp() {
        loginTest = new LoginTest();
        controller = new LoginController(loginTest);
    }

    /**
     * Comprova que si no s'introdueix cap usuari i contrasenya llenci excepció
     */
    @Test
    void testNoUserAndPasswordException() {
        loginTest.setUsername("");
        loginTest.setPassword("");
        @SuppressWarnings("unused")
        ControllerException ex = assertThrows(
                ControllerException.class,
                () -> controller.login()
        );
    }

    /**
     * Comprova que si no s'introdueix contrasenya es llença una excepció
     */
    @Test
    void testOnlyUserException() {
        loginTest.setUsername("Twiki");
        loginTest.setPassword("");
        @SuppressWarnings("unused")
        ControllerException ex = assertThrows(
                ControllerException.class,
                () -> controller.login()
        );
    }

    /**
     * Comprova que si no s'introdueix usuari es llença una excepció
     */
    @Test
    void testOnlyPasswordException() {
        loginTest.setUsername("");
        loginTest.setPassword("Tuki");
        @SuppressWarnings("unused")
        ControllerException ex = assertThrows(
                ControllerException.class,
                () -> controller.login()
        );
    }

    /**
     * Comprova que el controlador inicialitza correctament la vista de login i que no és nul·la.
     */
    @Test
    void testLoginFrameIsInitialized() {
        assertNotNull(controller.getLogin(), 
            "El controlador ha d'inicialitzar correctament la pantalla de login");
    }

    /**
     * Comprova que el botó de login rep un ActionListener quan s'inicialitza el controlador. 
     * Això indica que el controlador està preparat per reaccionar a l'acció de login.
     */
    @Test
    void testButtonHasActionListener() {
        var listeners = loginTest.getBtnLogin().getActionListeners();
        assertTrue(listeners.length > 0, 
            "El botó de login hauria de tenir com a mínim un ActionListener");
    }

    /**
     * Comprova que les pantalles AdminFrame i WaiterFrame no s'han creat encara
     * després d'inicialitzar el controlador (això només hauria de passar després
     * d'un login correcte, que no es prova aquí).
     */
    @Test
    void testAdminAndWaiterFramesInitiallyNull() {
        assertNull(controller.getAdminFrame(), 
            "AdminFrame hauria de ser nul després d'inicialitzar");
        assertNull(controller.getWaiterFrame(), 
            "WaiterFrame hauria de ser nul després d'inicialitzar");
    }

    /**
     * Comprova que la resposta d'usuari pot ser establerta i recuperada
     * sense errors.
     */
    @Test
    void testResponseUserAssignment() {
        LoginResponse response = new LoginResponse();
        response.role = "ADMIN";
        response.token = "abc123";

        controller.setResponseUser(response);

        assertEquals("ADMIN", controller.getResponseUser().role,
            "El rol de l'usuari s'hauria de conservar");
        assertEquals("abc123", controller.getResponseUser().token,
            "El token de l'usuari s'hauria de conservar");
    }

    
}

