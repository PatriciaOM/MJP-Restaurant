/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for holding the valid active session tokens. 
 * <p>
 * When an endpoint requires authentication it must receive a sessionToken, the session token must be validated using this class.
 * {@link mjp.server.ServerMJP.Controller.Controller#login} is responsible for adding the new valid sessionToken when a valid login is performed.
 * {@link mjp.server.ServerMJP.Controller.Controller#logout} is responsible for deleting the corresponding sessionToken when a valid logout is performed.
 * @author Joan Renau Valls
 */
@Component
public class SessionManager {
    /**
     * This property is responsible for mapping the session tokens to the {@link User} of the corresponding session.
     */
    ConcurrentHashMap<String, User> sessions = new ConcurrentHashMap<>();
    long nextSession = 0;
   
    
    public SessionManager() {}
    
    

    
    /**
     * Creates a new session for the user provided. 
     * @param user The user to be authorized.
     * @return The sessionToken associated with this login session.
     * It must be provided on successive calls to endpoints requiring authentication.
     */
    public String addSession(User user){
        String sessionToken = "" + this.nextSession;
        this.nextSession++;
        this.sessions.put("" + sessionToken, user);
      
        return sessionToken;
    }
    
    /**
     * Used to revoke a sessionToken after this call the token is not valid anymore
     * @param sessionToken
     * @return Returns true if the token was deleted properly.
     */
    public boolean removeSession(String sessionToken) {
        if (sessionToken == null)
            return false;
        User deletedUser = this.sessions.remove(sessionToken);
        return deletedUser != null;
    }
      
    /**
     * Returns the user associated with a session token.
     * @param sessionToken Te session token to be checked.
     * @return The {@link mjp.server.ServerMJP.database.User} associated with the provided sessionToken.
     */
    public User getUserByToken(String sessionToken) {
        return this.sessions.get(sessionToken);
    }
    
    /**
     * Checks if a session token has user credentials
     * @param sessionToken The session token to be validated
     * @return true if it has the right privileges
     */
    public boolean validateUserToken(String sessionToken)  {
        System.out.println("Validating user for token: " + sessionToken + "<<<");
        User user = this.getUserByToken(sessionToken);
        return user != null;
    }
    
    /**
     * Checks if a session token has admin credentials
     * @param sessionToken The session token to be validated
     * @return true if it has the right privileges
     */
    public boolean validateAdminToken(String sessionToken) {
        System.out.println("Validating admin token " + sessionToken);
        User user = this.getUserByToken(sessionToken);
        if (user == null)
            return false;
        return user.getRole() == UserRole.ADMIN;
    }
}
