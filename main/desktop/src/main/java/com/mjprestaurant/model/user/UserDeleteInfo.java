package com.mjprestaurant.model.user;

/**
 * Classe que conté la informació necessària per eliminar un usuari.
 * @author Patricia Oliva
 */
public class UserDeleteInfo {

    private String username;
    private String sessionToken;

    /**
     * Constructor principal
     * @param sessionToken token de la sessió de l'usuari
     * @param username nom d'usuari a eliminar
     */
    public UserDeleteInfo(String sessionToken, String username) {
        this.sessionToken = sessionToken;
        this.username = username;
    }

    /**
     * Inicialitza el nom d'usuari a eliminar
     * @param val nom d'usuari
     */
    void setUserName(String val) {
        this.username = val;
    }

    /**
     * Retorna el nom d'usuari a eliminar
     * @return nom d'usuari
     */
    public String getUserName() {
        return this.username;
    }

    /**
     * Inicialitza el token de sessió
     * @param val token de sessió
     */
    void setSessionToken(String val) {
        this.sessionToken = val;
    }

    /**
     * Retorna el token de sessió
     * @return token de sessió
     */
    public String getSessionToken() {
        return this.sessionToken;
    }

}
