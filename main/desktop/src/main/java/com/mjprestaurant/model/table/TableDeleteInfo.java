package com.mjprestaurant.model.table;

public class TableDeleteInfo {
    
    private Long id;
    private String sessionToken;
    
    public TableDeleteInfo() {
        
    }
    
    public TableDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }
    
    void setId(Long val) {
        this.id = val;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    public Object getMessageData() {
        return null;
    }
}
