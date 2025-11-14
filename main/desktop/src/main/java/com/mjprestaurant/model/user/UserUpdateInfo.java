package com.mjprestaurant.model.user;

public class UserUpdateInfo {
    
    private User user;
    private String sessionToken;
    
    
    public UserUpdateInfo(String sessionToken, User user) {
        this.user = user;
        this.sessionToken = sessionToken;
    }
    
    void setUser(User val) {
        this.user = val;
    }
    
    public User getUser() {
        return this.user;
    }
    
    void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
    
}
