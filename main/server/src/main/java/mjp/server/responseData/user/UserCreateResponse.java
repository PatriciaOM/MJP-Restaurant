/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.user;

/**
 *
 * @author twiki
 */
public class UserCreateResponse {
    public String message="success";
    
    public UserCreateResponse(){
    }
    
    /**
     * 
     * @param message Returns a log out message on success.
     */
    public UserCreateResponse(String message){
        this.message = message;
    }
    
    
}
