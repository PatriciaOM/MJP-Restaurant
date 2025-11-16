package com.mjprestaurant.model.user;

/**
 * Classe per crear usuaris
 * @author Patricia Oliva
 */
public class UserCreateInfo {
    private User user;
    private String sessionToken;
    
    /**
     * Constructor principal
     * @param sessionToken token 
     * @param user usuari a crear
     */
    public UserCreateInfo(String sessionToken, User user) {
        this.user = user;
        this.sessionToken = sessionToken;
    }
    
    /**
     * Inicialitza l'usuari a crear
     * @param val usuari a crear
     */
    public void setUser(User val) {
        this.user = val;
    }
    
    /**
     * Retorna l'usuari
     * @return usuari a crear
     */
    public User getUser() {
        return this.user;
    }
    
    /**
     * Inicialitza token de sessió
     * @param val token
     */
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    /**
     * Retorna el token de sessió
     * @return token
     */
    public String getSessionToken() {
        return this.sessionToken;
    }
    
}
