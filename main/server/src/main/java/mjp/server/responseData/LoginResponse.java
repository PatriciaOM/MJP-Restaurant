/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData;

/**
 *
 * @author twiki
 */
public class LoginResponse {
    public String token;
    
    public LoginResponse(){
    }
    
    public LoginResponse(String token){
        this.token = token;
    }
}
