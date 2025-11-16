package com.mjprestaurant.model.user;

/**
 * Classe que encapsula la informació necessària per actualitzar un usuari al servidor.
 */
public class UserUpdateInfo {

    private User user;
    private String sessionToken;

    /**
     * Constructor principal.
     * @param sessionToken token de sessió
     * @param user usuari a actualitzar
     */
    public UserUpdateInfo(String sessionToken, User user) {
        this.user = user;
        this.sessionToken = sessionToken;
    }

    /**
     * Inicialitza l'usuari a actualitzar (setter intern).
     * @param val usuari
     */
    public void setUser(User val) {
        this.user = val;
    }

    /**
     * Retorna l'usuari a actualitzar.
     * @return usuari
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Inicialitza el token de sessió (setter intern).
     * @param val token de sessió
     */
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }

    /**
     * Retorna el token de sessió.
     * @return token de sessió
     */
    public String getSessionToken() {
        return this.sessionToken;
    }

}
