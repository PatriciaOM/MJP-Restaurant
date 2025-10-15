package com.mjprestaurant.model;

/**
 * Classe amb la resposta del logout 
 */
public class LogoutResponse {
    public String message;
    
    /**
     * Constructor bàsic
     */
    public LogoutResponse(){
    }
    
    /**
     * Constructor principal amb el missatge del logout
     * @param message Missatge del logout
     */
    public LogoutResponse(String message){
        this.message = message;
    }
}
