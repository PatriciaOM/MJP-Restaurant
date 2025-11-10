/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.table;

import mjp.server.responseData.user.*;
import java.util.List;
import mjp.server.ServerMJP.database.User;

/**
 *
 * @author twiki
 */
public class TableDeleteResponse {
    public String message="success";
    
    public TableDeleteResponse(){
    }
    
    /**
     * 
     * @param message Returns a log out message on success.
     */
    public TableDeleteResponse(String message){
        this.message = message;
    }

    public String getMessage() {return this.message;}
    
    
}
