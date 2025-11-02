/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.user;

import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;

/**
 *
 * @author twiki
 */
public class UserCreateInfo extends User {
    
    private User user;
    private String sessionToken;
    
    
    public UserCreateInfo(String sessionToken, User user) {
        this.user = user;
        this.sessionToken = sessionToken;
    }
    
    void setUser(User val) {
        this.user = val;
    }
    
    public User getUser() {
        return this.user;
    }
    
    void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    
}
