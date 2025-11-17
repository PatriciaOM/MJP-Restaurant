/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.table;

import mjp.server.queryData.user.*;
import com.google.gson.Gson;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;


/**
 * Class for holding the information of a Table create request
 * @author Joan Renau Valls
 */
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
