package com.mjprestaurant.model;

public class UserDeleteInfo {
    
    private String username;
    private String sessionToken;
    
    
    public UserDeleteInfo(String sessionToken, String username) {
        this.sessionToken = sessionToken;
        this.username = username;
    }
    
    void setUserName(String val) {
        this.username = val;
    }
    
    public String getUserName() {
        return this.username;
    }
    
    void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

}
