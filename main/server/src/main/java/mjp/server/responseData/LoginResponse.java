/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData;

import mjp.server.dataClasses.UserRole;

/**
 *
 * @author twiki
 */
public class LoginResponse {
    public String token;
    public String role;
    
    public LoginResponse(){
    }
    
    public LoginResponse(String token, UserRole role){
        this.token = token;
        this.role = role.getRole();
    }   
}
