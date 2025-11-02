/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.user;

import java.util.List;
import mjp.server.ServerMJP.database.User;

/**
 *
 * @author twiki
 */
public class UserGetResponse {
    
    private List<User> users;
    
    public UserGetResponse(List<User> users) {
        this.users = users;
    }
    
    void setUser(List<User> val) {
        this.users = val;
    }
    
    public List<User> getUser() {
        return this.users;
    }
    
}
