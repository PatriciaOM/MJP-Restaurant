package com.mjprestaurant.model;

/**
 * Classe amb la informació que s'envia del logout
 * @author Patricia Oliva
 */
public class LogoutInfo {
    String sessionToken;
    
    /**
     * Constructor bàsic
     */
    public LogoutInfo(){};
    
    /**
     * Constructor principal amb el token (id) de sessió
     * @param sessionToken id de sessió
     */
    public LogoutInfo(String sessionToken){
        this.sessionToken = sessionToken;
    }
    
    /**
     * Inicialitza el token de sessió
     * @param sessionToken id de sessió
     */
    public void setSessionToken(String sessionToken){this.sessionToken = sessionToken;}
    /**
     * Retorna el token de sessió
     * @return token de sessió
     */
    public String getSessionToken(){return this.sessionToken;}
    
}
