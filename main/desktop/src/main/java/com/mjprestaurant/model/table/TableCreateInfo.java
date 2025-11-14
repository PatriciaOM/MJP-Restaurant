package com.mjprestaurant.model.table;

public class TableCreateInfo {
    private String sessionToken;
    private TableRestaurant table;
    
    
   public TableCreateInfo(){};
   
    public TableCreateInfo(String sessionToken, TableRestaurant table) {
        this.sessionToken = sessionToken;
        this.table = table;
    }  
        
    public TableCreateInfo(TableCreateInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.table = orig.getTable();
    }
           
    void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    public TableRestaurant getTable() {
        return table;
    }

    public void setTable(TableRestaurant table) {
        this.table = table;
    }

}
