/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.ProcessBuilder.Redirect.Type;
import mjp.server.dataClasses.UserRole;

/**
 *
 * @author twiki
 */

public class UserResponse {
    public boolean logged;
    public String role;
    
    public UserResponse(boolean logged, UserRole role){
        if (role == null)
            throw new IllegalArgumentException(String.format("UserResponse: null is not permited. Arguments: %s %s.", logged, role));
        this.logged = logged;
        this.role = role.getRole();
    }
    
    public void setLogged(boolean logged){
        this.logged = logged;
    }
    
    public boolean getLogged(){
        return this.logged;
    }
     
    public void setRole(UserRole role){
        this.role = role.getRole();
    }
    
    public UserRole getRole(){
        return UserRole.fromString(this.role);
    }
            
}

//public class UnknownUserResponse {
//    public boolean logged;
//    Gson jsonConverter;
//    
//    
//    public String toJson(){
//        java.lang.reflect.Type type = new TypeToken<UnknownUserResponse>(){}.getType();
//        return this.jsonConverter.toJson(this, type);
//    }
//    
//    public UnknownUserResponse() {
//        this.jsonConverter = new Gson();
//        
//    }
//    public UnknownUserResponse(boolean logged) {
//        this.jsonConverter = new Gson();
//        this.logged = logged;
//    }
//    
//    public void setLogged(boolean logged){
//        this.logged = logged;
//    }
//    
//    public boolean getLogged(){
//        return this.logged;
//    }
//    
//}
