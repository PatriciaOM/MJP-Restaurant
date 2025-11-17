/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData;

/**
 * Endpoint that returns the current status of the tables.
 * @author Joan Renau Valls
 */
public class TableStatusInfo implements AuthorizedQueryInfo{
    /**
     * A valid session token obtained by login
     */
    private String sessionToken;
    /**
     * The id of the table to get info for.
     */
    private String tableId;
    
    public TableStatusInfo(String sessionToken, String tableId) {
        this.sessionToken = sessionToken;
        this.tableId = tableId;
    }
    
    public void setSessionToken(String value){
        this.sessionToken = value;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    public void setTableId(String value){
        this.tableId = value;
    }
    
    public String getTableId() {
        return this.tableId;
    }


    @Override
    public void setMessageData(Object requestItem) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getMessageData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
