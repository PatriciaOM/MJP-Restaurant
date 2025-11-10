/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import com.google.gson.Gson;
import static java.awt.SystemColor.info;
import java.util.ArrayList;
import java.util.List;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.LoginInfo;
import mjp.server.queryData.LogoutInfo;
import mjp.server.responseData.LoginResponse;
import mjp.server.responseData.LogoutResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import mjp.server.ServerMJP.database.UserRepository;
import mjp.server.queryData.TableStatusInfo;
import mjp.server.responseData.TableStatusResponse;
import mjp.server.responseData.TableStatusResponseElement;

/**
 * This class is responsible for handling the the requests received by the {@link mjp.server.ServerMJP.Controller.Controller}.
 * 
 * The Controller is responsible for specifying the endpoints.
 * Each method of this class responsible of an endpoint expects in an instance of a class of the package {@link mjp.server.queryData}
 * Each method responsible of an endpoint returns an instance of a class of the package {@link mjp.server.responseData}
 *
 * @author Joan Renau Valls
 */
@Component
public class AuthenticationManager {
//    private SessionManager sessionManager = new SessionManager();
    private SessionManager sessionManager;
    private UserRepository userRepository;
    Gson gson = new Gson();
    
    public AuthenticationManager(UserRepository userRepository, SessionManager sessionManager){
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
    };
       
    /**
     * Method for handling the data of {@link mjp.server.ServerMJP.Controller.Controller#login} endpoint. See {@link AuthenticationManager}
     * 
     * @return 
     */
    public LoginResponse login(LoginInfo info) {
        if (info.getPassword() == null || info.getUsername() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        String username = info.getUsername();
        String password = info.getPassword();
        System.out.println(String.format("Loggin attempt with username: %s, password: %s.", username, password));        
        List<User> allMatchingUsers = this.userRepository.findByUsername(username);
        if (allMatchingUsers.size() != 1) 
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        User user = allMatchingUsers.get(0);
        if (!user.getPassword().equals(password))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        String sessionToken = this.sessionManager.addSession(user);
        return new LoginResponse(sessionToken, user.getRole());
    }
         
    /**
     * Method for handling the data of {@link mjp.server.ServerMJP.Controller.Controller#logout} endpoint. See {@link AuthenticationManager}
     * 
     * @return 
     */
    public LogoutResponse logout(LogoutInfo info) {
        if (info.getSessionToken() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        boolean loggedOut = this.sessionManager.removeSession(info.getSessionToken());
        return new LogoutResponse("Logged out");
    }
    
          
    /**
     * Returns the user associated with a session token.
     * @param sessionToken Te session token to be checked.
     * @return The {@link mjp.server.ServerMJP.database.User} associated with the provided sessionToken.
     */
    public User getUserByToken(String sessionToken) {
        
        return this.sessionManager.getUserByToken(sessionToken);
    }
     
    
}
