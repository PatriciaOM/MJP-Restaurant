/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData;

/**
 *
 * @author twiki
 */
public class TableStatusInfo {
    private String sessionToken;
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
    
}
