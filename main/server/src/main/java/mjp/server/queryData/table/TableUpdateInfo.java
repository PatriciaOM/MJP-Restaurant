/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.table;

import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.queryData.user.*;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;

/**
 *
 * @author twiki
 */
public class TableUpdateInfo {
    
    private String sessionToken;
    private TableRestaurant table;
    
    
    public TableUpdateInfo(String sessionToken, TableRestaurant table) {
        this.sessionToken = sessionToken;
        this.table = table;
    }
    
    void setTable(TableRestaurant val) {
        this.table = val;
    }
    
    public TableRestaurant getTable() {
        return this.table;
    }
    
    void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    
}
