/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.user;

/**
 * Class for holding the information of an User delete request
 * @author Joan Renau Valls
 */
public class UserDeleteInfo {
    
    private String username;
    private String sessionToken;
    
    
    public UserDeleteInfo(String sessionToken, String username) {
        this.sessionToken = sessionToken;
        this.username = username;
    }
    
    void setUserName(String val) {
        this.username = val;
    }
    
    public String getUserName() {
        return this.username;
    }
    
    void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
}
