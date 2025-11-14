package com.mjprestaurant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mjprestaurant.model.user.UserResponse;
import com.mjprestaurant.model.user.UserRole;

/**
 * Classe de test pel model
 * @author Patricia Oliva
 */
public class ModelTests {
    
    /**
     * Comprova que el constructor amb paràmetres inicialitza correctament els valors enviats.
     */
    @Test
    void testConstructorWithParameters() {
        UserResponse ur = new UserResponse(true, UserRole.ADMIN);
        assertTrue(ur.getLogged(), "Ha d'estar loguejat");
        assertEquals(UserRole.ADMIN, ur.getRole(), "El rol ha de ser ADMIN");
    }

    /**
     * Comprova que es llença una excepció si es crea un userResponse amb rol null
     */
    @Test
    void testConstructorWithNullRole() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new UserResponse(true, null);
        });
        assertTrue(exception.getMessage().contains("null is not permited"), "Ha de llançar excepció si el rol és null");
    }

    /**
     * Comprova que els mètodes get i set del log funcionin correctament
     */
    @Test
    void testSetAndGetLogged() {
        UserResponse ur = new UserResponse();
        ur.setLogged(true);
        assertTrue(ur.getLogged(), "Ha de retornar true després de setLogged");

        ur.setLogged(false);
        assertFalse(ur.getLogged(), "Ha de retornar true després de setLogged");
    }

    /**
     * Comprova que els mètodes get i set del userResponse funcionin correctament
     */
    @Test
    void testSetAndGetRole() {
        UserResponse ur = new UserResponse();
        ur.setRole(UserRole.USER);
        assertEquals(UserRole.USER, ur.getRole(), "El rol ha de ser USER");

        ur.setRole(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, ur.getRole(), "El rol ha de ser ADMIN");
    }

    /**
     * Comprova que els mètode de UserRole fromString retorni el rol correcte tenint en compte la cadena introduïda
     */
    @Test
    void testFromStringValidRoles() {
        assertEquals(UserRole.ADMIN, UserRole.fromString("admin"), "Ha de retornar ADMIN per 'admin'");
        assertEquals(UserRole.USER, UserRole.fromString("user"), "Ha de retornar USER per 'user'");
    }

    /**
     * Comprova que es llenci una excepció si es passa un valor invàlid com a rol
     */
    @Test
    void testFromStringInvalidRole() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            UserRole.fromString("manager");
        });
        assertTrue(exception.getMessage().contains("Invalid role"), "Ha de llençar una excepció per un rol invàlid");
    }

    /**
     * Comprova que a la classe LoginResponse els valors passats per paràmetre al constructor coincideixen amb els que s'emmagatzemen
     */
    @Test
    void testConstructorLoginResponse() {
        LoginResponse lr = new LoginResponse("12345", UserRole.ADMIN);
        assertEquals("12345", lr.getToken(), "El token ha de coincidir amb el passat al constructor");
        assertEquals("admin", lr.getRole(), "El rol ha de coincidir amb el UserRole passat al constructor");
    }

    /**
     * Comprova el get i el set del token de LoginResponse
     */
    @Test
    void testSetGetToken() {
        LoginResponse lr = new LoginResponse();
        lr.setToken("tokenXYZ");
        assertEquals("tokenXYZ", lr.getToken(), "El token retornat ha de coincidir amb el que s'ha assignat");
    }

    /**
     * Comprova el get i el set del rol de LoginResponse
     */
    @Test
    void testSetGetRole() {
        LoginResponse lr = new LoginResponse();
        lr.setRole("user");
        assertEquals("user", lr.getRole(), "El rol retornat ha de coincidir amb el que s'ha assignat");
    }

    /**
     * Comprova el get i el set del missatge passat per paràmetre a LogoutResponse
     */
    @Test
    void testConstructorLogoutResponse() {
        LogoutResponse lr = new LogoutResponse("Sessió tancada correctament");
        assertEquals("Sessió tancada correctament", lr.getMessage(), "El missatge ha de coincidir amb el passat al constructor");
    }

}
