/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData;


/**
 * This class is responsible for holding the return information sent to the endpoint defined in {@link mjp.server.ServerMJP.Controller.Controller#logout}
 * @author Joan Renau Valls
 */
public class LogoutResponse {
    public String message;
    
    public LogoutResponse(){
    }
    
    /**
     * 
     * @param message Returns a log out message on success.
     */
    public LogoutResponse(String message){
        this.message = message;
    }
    
}
