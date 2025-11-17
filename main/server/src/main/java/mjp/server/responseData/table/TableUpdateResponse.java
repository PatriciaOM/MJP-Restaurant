/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.table;

import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.responseData.user.*;
import mjp.server.ServerMJP.database.User;

/**
 * Class for the table update responses.
 * @author Joan Renau Valls
 */
public class TableUpdateResponse {
    public String message="success";
    public TableRestaurant table;
    
    public TableUpdateResponse(){
    }
    
    
    public TableUpdateResponse(String message, TableRestaurant table){
        this.message = message;
        this.table = table;
    }
    
    /**
     * 
     * @param message Returns a log out message on success.
     */
    public TableUpdateResponse(String message){
        this.message = message;
    }
    
    public TableUpdateResponse(TableRestaurant table){
        this.table = table;
    }
    
    
    public void setMessage(String value) {
        this.message = value;
    }
    
    public String getMessage() {
        return this.message;
    }

    public void setTable(TableRestaurant table) {
        this.table = table;
    }
    
    public TableRestaurant getTable() {
        return this.table;
    }
    
    
    
}
