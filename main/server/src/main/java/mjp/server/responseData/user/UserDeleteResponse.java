/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.user;

import java.util.List;
import mjp.server.ServerMJP.database.User;

/**
 * Class for the User delete responses.
 * @author Joan Renau Valls
 */
public class UserDeleteResponse {
    public String message="success";
    
    public UserDeleteResponse(){
    }
    
    /**
     * 
     * @param message Returns a log out message on success.
     */
    public UserDeleteResponse(String message){
        this.message = message;
    }

    public String getMessage() {return this.message;}
    
    
}
