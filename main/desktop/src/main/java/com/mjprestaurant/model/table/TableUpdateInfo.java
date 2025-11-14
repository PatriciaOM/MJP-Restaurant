package com.mjprestaurant.model.table;

/**
 *
 * @author twiki
 */
public class TableUpdateInfo {
    
    private String sessionToken;
    private TableRestaurant table;
    
    public TableUpdateInfo(){};
    
    public TableUpdateInfo(String sessionToken, TableRestaurant table) {
        this.sessionToken = sessionToken;
        this.table = table;
    }
    
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    void setTable(TableRestaurant val) {
        this.table = val;
    }
    
    public TableRestaurant getTable() {
        return this.table;
    }

    public void setMessageData(TableRestaurant requestItem) {
        this.setTable(requestItem);
    }

    public TableRestaurant getMessageData() {
        return this.getTable();
    }

}
