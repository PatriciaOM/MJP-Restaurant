package com.mjprestaurant.model;

/* Classe necessària per l'enviament del token al tancar sessió */
public class LogoutInfo {
    String sessionToken;
    
    public LogoutInfo(){};
    
    public LogoutInfo(String sessionToken){
        this.sessionToken = sessionToken;
    }
    
    public void setSessionToken(String sessionToken){this.sessionToken = sessionToken;}
    public String getSessionToken(){return this.sessionToken;}
    
}
