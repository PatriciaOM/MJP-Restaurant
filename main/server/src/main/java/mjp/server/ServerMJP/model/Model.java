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
public class Model {
    private SessionManager sessionManager= new SessionManager();
    private UserRepository userRepository;
    Gson gson = new Gson();
    
    public Model(UserRepository userRepository){
        this.userRepository = userRepository;
    };
       
    /**
     * Method for handling the data of {@link mjp.server.ServerMJP.Controller.Controller#login} endpoint. See {@link Model}
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
     * Method for handling the data of {@link mjp.server.ServerMJP.Controller.Controller#logout} endpoint. See {@link Model}
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
     * Method for handling the data of {@link mjp.server.ServerMJP.Controller.Controller#sessionStatus} endpoint. See {@link Model}
     * 
     * @return 
     */
    public User getUserByToken(String token){
        return sessionManager.getUserByToken(token);
    }
    
    /**
     * Method for handling the data of {@link mjp.server.ServerMJP.Controller.Controller#allusers} endpoint. See {@link Model}
     * 
     * @return 
     */
    public String allUsers() {
        ArrayList<User> users = new ArrayList();
        String result = "";
        
        userRepository.findAll().forEach( user -> {
            users.add(user);
        });
        
        for (User user: users) {
            result += user.toString() + '\n';
        }
        
        return "users: " + result;
    }
    
    /**
     * Creates mock data for debug and development purposes.
     */
    public void mockData() {
        User user;
        
        user = new User("Twiki", "Tuki", UserRole.USER);
        this.userRepository.save(user) ;
        user = new User("Ping", "Pong", UserRole.ADMIN);
        this.userRepository.save(user) ;
    }
    
}
