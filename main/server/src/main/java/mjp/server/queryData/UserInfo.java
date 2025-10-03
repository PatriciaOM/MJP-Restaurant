/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData;

/**
 *
 * @author twiki
 */
public class UserInfo {
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
