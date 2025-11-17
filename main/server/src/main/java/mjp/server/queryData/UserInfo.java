/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData;

/**
 * This class is responsible for holding the information sent to the endpoint defined in @link mjp.server.ServerMJP.Controller.Controller#userInfo
 * @author Joan Renau Valls
 */
public class UserInfo {
    /**
     * A valid session token obtained by login
     */
    String sessionToken;
    
    UserInfo(){};
    
    UserInfo(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    public String getDataError() {
        if (sessionToken == null)
            return "session token is null";
        return null;
    }
}
