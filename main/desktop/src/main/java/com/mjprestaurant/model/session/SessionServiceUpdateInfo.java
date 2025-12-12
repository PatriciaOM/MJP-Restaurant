package com.mjprestaurant.model.session;


public class SessionServiceUpdateInfo {   
    private String sessionToken;
    private SessionService item; 
    
    public SessionServiceUpdateInfo(){};
   
    public SessionServiceUpdateInfo(String sessionToken, SessionService sessionService) {
        this.sessionToken = sessionToken;
        this.item = sessionService;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public SessionService getItem() {
        return item;
    }

    public void setItem(SessionService item) {
        this.item = item;
    }  

    
}
