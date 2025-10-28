/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package mjp.server.ServerMJP.Controller;

import mjp.server.ServerMJP.model.SessionManager;
import mjp.server.responseData.UserResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import mjp.server.ServerMJP.ServerMjpApplication;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.LoginInfo;
import mjp.server.queryData.LogoutInfo;
import mjp.server.responseData.LoginResponse;
import mjp.server.responseData.LogoutResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import mjp.server.ServerMJP.model.Model;
import mjp.server.ServerMJP.database.UserRepository;



/**
 * Main class for setting the server's endpoints
 * @author Joan Renu Valls
 */

@RestController
public class Controller {
    /**
     * Is used for encoding and decoding to and from Json.
     * 
     * This instances is mainly used for storing into objects the jsons received on the requests.
     * And storing the response data into json objects ready to be sent.
     */
    private Gson gson;
    /**
     * This object is responsible for the logic of handling the the requests.
     */
    private Model model;
    /**
     * Used for login purposes
     */
    final Logger log = LoggerFactory.getLogger(ServerMjpApplication.class);
    
    public Controller(
        Model model,
        UserRepository aplicationRepository
    ){
        this.gson = new Gson();
        this.model = model;
        this.model.mockData();
    }
    
    /**
     * This endpoint handles a login request for a user and password see {@link LoginInfo} to check the parameters
     * 
     * @param loginInfo contains the request information
     * @return 
     */
    @PostMapping("login")
    public String login(@RequestBody LoginInfo loginInfo){ 
        LoginResponse response = this.model.login(loginInfo);
        String responseJSON = this.gson.toJson(response);
        return this.gson.toJson(response);
    }   
  
    /**
     * This endpoint handles a logout request for a user and password see {@link LogoutInfo} to check the parameters
     * 
     * @param logoutInfo contains the request information
     */
    @PostMapping("logout")
    public String logout(@RequestBody LogoutInfo logoutInfo){
        LogoutResponse response = this.model.logout(logoutInfo);
        return this.gson.toJson(response);
    }
    
    /**
     * This endpoint receives a session token and returns the user associated with it. It is just for development purposes.
     * @param sessionToken
     * @return returns a {@link User} in Json format or null if the session token is not valid
     */
    @GetMapping("sessionstatus")
    public String sessionStatus(@RequestParam String sessionToken) {
        User user = this.model.getUserByToken(sessionToken);
        if (user == null)
            return "null";
        return this.gson.toJson(user);
    }
            
    /**
     *  This endpoint returns a list of all users in the database. It is just for development purposes.
     * @return returns a list with all the users.
     */
    @GetMapping("allusers")
    public String allusers() { 
        String users = this.model.allUsers();
//        log.info(users);
        return users;
    }
    
    @GetMapping("hello")
    public String hello() { 
        String result = "hello";
        return result;
    }
}