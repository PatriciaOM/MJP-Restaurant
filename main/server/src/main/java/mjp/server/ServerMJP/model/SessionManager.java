/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import mjp.server.ServerMJP.database.User;

/**
 *
 * @author Joan Renau Valls
 */
public class SessionManager {
    ConcurrentHashMap<String, User> sessions = new ConcurrentHashMap<>();
    long nextSession = 0;
   
    
    public SessionManager() {}
    
    public String addSession(User user){
        String sessionToken = "" + this.nextSession;
        this.nextSession++;
        this.sessions.put("" + sessionToken, user);
        return sessionToken;
    }
    
    public boolean removeSession(String sessionToken) {
        User deletedUser = this.sessions.remove(sessionToken);
        return deletedUser != null;
    }
    
    public User getUserByToken(String sessionToken) {
        return this.sessions.get(sessionToken);
    }
}
