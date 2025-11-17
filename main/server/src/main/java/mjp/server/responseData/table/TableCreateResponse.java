/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.table;

import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.responseData.user.*;
import mjp.server.ServerMJP.database.User;

/**
 * Class for the table create responses.
 * @author Joan Renau Valls
 */
public class TableCreateResponse {
    public String message="success";
    public TableRestaurant table;
    
    public TableCreateResponse(){
    }
    
    /**
     * 
     * @param message Returns a log out message on success.
     */
    public TableCreateResponse(String message, TableRestaurant table){
        this.message = message;
        this.table = table;
    }
    
    public TableCreateResponse(TableRestaurant table){
        this.table = table;
    }
    
    public TableRestaurant getTable(){
        return this.table;
    }

    public void setTable(TableRestaurant table) {
        this.table = table;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
    
    
    
}
