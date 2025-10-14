package com.mjprestaurant.model;

/**
 * Classe per l'objecte User, amb el nom d'usuari i la contrasenya
 */
public class User {
    private String username;
    private String password;
    
    /**
     * Constructor bàsic
     */
    public User() {
    }

    /**
     * Constructor principal amb nom i contrasenya
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Retorna el nom de l'usuari
     * @return nom del user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Inicialitza el nom de l'usuari
     * @param username
     */
    public void setUserame(String username) {
        this.username = username;
    }

    /**
     * Retorna la contrasenya
     * @return contrasenya
     */
    public String getPassword() {
        return password;
    }

    /**
     * Inicialitza la contrasenya
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
