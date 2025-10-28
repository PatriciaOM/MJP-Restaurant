/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData;

import mjp.server.dataClasses.UserRole;

/**
 * This class is responsible for holding the return information sent to the endpoint defined in {@link mjp.server.ServerMJP.Controller.Controller#login}
 * @author Joan Renau Valls
 */
public class LoginResponse {
    public String token;
    public String role;
    
    public LoginResponse(){
    }
    
    /**
     * @param token The sessionToken crated for this session. It will have to be returned on successive calls to endpoints with authentication required.
     * @param role The role of the user that is being logged in.
     */
    public LoginResponse(String token, UserRole role){
        this.token = token;
        this.role = role.getRole();
    }   
    
    public String toString(){
        return (String.format("{token: %s, role: %s}", this.token, this.role));
    }
}
