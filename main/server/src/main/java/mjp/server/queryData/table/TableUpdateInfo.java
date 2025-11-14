/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.table;

import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.queryData.user.*;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;

/**
 *
 * @author twiki
 */
public class TableUpdateInfo implements AuthorizedQueryInfo<TableRestaurant> {
    
    private String sessionToken;
    private TableRestaurant table;
    
    public TableUpdateInfo(){};
    
    public TableUpdateInfo(String sessionToken, TableRestaurant table) {
        this.sessionToken = sessionToken;
        this.table = table;
    }
    
    

//    @Override
//    public AuthorizedQueryInfo createInsance(String sessionToken, TableRestaurant requestItem) {
//        return new TableUpdateInfo(sessionToken, requestItem);
//    }
    
    @Override
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    @Override
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    void setTable(TableRestaurant val) {
        this.table = val;
    }
    
    public TableRestaurant getTable() {
        return this.table;
    }

    @Override
    public void setMessageData(TableRestaurant requestItem) {
        this.setTable(requestItem);
    }

    @Override
    public TableRestaurant getMessageData() {
        return this.getTable();
    }
}
