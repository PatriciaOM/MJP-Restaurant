/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.user;

import mjp.server.ServerMJP.database.User;

/**
 *
 * @author twiki
 */
public class UserUpdateResponse {
    public String message="success";
    public User user;
    
    public UserUpdateResponse(){
    }
    
    
    public UserUpdateResponse(String message, User user){
        this.message = message;
        this.user = user;
    }
    
    /**
     * 
     * @param message Returns a log out message on success.
     */
    public UserUpdateResponse(String message){
        this.message = message;
    }
    
    public UserUpdateResponse(User user){
        this.user = user;
    }
    
    
    public void setMessage(String value) {
        this.message = value;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setUser(User value) {
        this.user = value;
    }
    
    public User getUser() {
        return this.user;
    }
    
    
    
}
