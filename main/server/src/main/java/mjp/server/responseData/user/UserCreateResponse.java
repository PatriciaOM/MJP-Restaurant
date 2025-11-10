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
public class UserCreateResponse {
    public String message="success";
    public User user;
    
    public UserCreateResponse(){
    }
    
    /**
     * 
     * @param message Returns a log out message on success.
     */
    public UserCreateResponse(String message, User user){
        this.message = message;
        this.user = user;
    }
    
    public UserCreateResponse(User user){
        this.user = user;
    }
    
    public User getUser(){
        return this.user;
    }
    
    public void setUser(User value){
        this.user = value;
    }
    
    
}
