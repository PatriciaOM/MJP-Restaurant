package com.mjprestaurant.model.table;

/**
 * Endpoint that returns the current status of the tables.
 * @author Joan Renau Valls
 */
public class TableStatusInfo{
    /**
     * A valid session token obtained by login
     */
    public String sessionToken;
    /**
     * The id of the table to get info for.
     */
    public Long tableId;
    
    public Long sessionServiceId;
     
    public TableStatusInfo() {}
      
    public TableStatusInfo(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    
//    
//    public TableStatusInfo(String sessionToken, String tableId) {
//        this.sessionToken = sessionToken;
//        this.tableId = Long.parseLong(tableId);
//    }
    
    public TableStatusInfo(String sessionToken, Long tableId) {
        this.sessionToken = sessionToken;
        this.tableId = tableId;
    }
    
    public void setSessionToken(String value){
        this.sessionToken = value;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    public void setTableId(Long value){
        this.tableId = value;
    }
    
    public Long getTableId() {
        return this.tableId;
    }

    public void setSessionServiceId(Long sessionServiceId) {
        this.sessionServiceId = sessionServiceId;
    }

    public Long getSessionServiceId() {
        return sessionServiceId;
    }

    
}
