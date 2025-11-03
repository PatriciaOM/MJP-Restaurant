/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package mjp.server.ServerMJP.Controller;

import mjp.server.ServerMJP.model.SessionManager;
import mjp.server.responseData.UserResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
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
import mjp.server.ServerMJP.model.AuthenticationManager;
import mjp.server.ServerMJP.database.UserRepository;
import mjp.server.ServerMJP.model.TableManager;
import mjp.server.ServerMJP.model.UserManager;
import mjp.server.queryData.TableStatusInfo;
import mjp.server.queryData.user.UserCreateInfo;
import mjp.server.queryData.user.UserDeleteInfo;
import mjp.server.queryData.user.UserGetInfo;
import mjp.server.queryData.user.UserUpdateInfo;
import mjp.server.responseData.TableStatusResponse;
import mjp.server.responseData.user.UserCreateResponse;
import mjp.server.responseData.user.UserDeleteResponse;
import mjp.server.responseData.user.UserGetResponse;
import mjp.server.responseData.user.UserUpdateResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;



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
//    private Gson gson;
    private Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
    /**
     * This object is responsible for the logic of handling the the requests.
     */
    private AuthenticationManager model;
    
    final TableManager tableManager;
    final UserManager userManager;
    
    /**
     * Used for login purposes
     */
    final Logger log = LoggerFactory.getLogger(ServerMjpApplication.class);
    
    public Controller(
        UserRepository aplicationRepository,
        AuthenticationManager model,
        TableManager tableManager,
        UserManager userManager
    ){
//        this.gson = new Gson();
        this.model = model;
        this.tableManager = tableManager;
        this.userManager = userManager;
        
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
        String responseJSON = this.gson.toJson(response); // TODO Delete
        return this.gson.toJson(response);
    }   
  
    /**
     * This endpoint handles a logout request for a user and password see {@link LogoutInfo} to check the parameters
     * 
     * @param logoutInfo contains the request information
     * @return 
     */
    @PostMapping("logout")
    public String logout(@RequestBody LogoutInfo logoutInfo){
        LogoutResponse response = this.model.logout(logoutInfo);
        return this.gson.toJson(response);
    }
     
    @PostMapping("user/create")
    public String userCreate(@RequestBody UserCreateInfo user){
//        this.log.info(String.format("POST user/create (%s)", this.gson.toJson(user)));
        System.out.println(String.format("POST user/create (%s)", this.gson.toJson(user)));
        UserCreateResponse response  = this.userManager.create(user);
        System.out.println("Returning resposne for user/create");
        return this.gson.toJson(response);
    }
    
    @PostMapping("user/get")
    public String UserGet(@RequestBody UserGetInfo info){
        System.out.println(String.format("POST user/get (%s)", this.gson.toJson(info)));
        UserGetResponse response = this.userManager.get(info);
        return this.gson.toJson(response);
    }  
    
    @PostMapping("user/delete")
    public String UserDelete(@RequestBody UserDeleteInfo info){
        System.out.println(String.format("POST user/delete (%s)", this.gson.toJson(info)));
        UserDeleteResponse response = this.userManager.delete(info);
        return this.gson.toJson(response);
    }
    
    @PostMapping("user/update")
    public String UserUpdate(@RequestBody UserUpdateInfo info){
        System.out.println(String.format("POST user/update (%s)", this.gson.toJson(info)));
        
        UserUpdateResponse response = this.userManager.update(info);
        return this.gson.toJson(response);
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    /**
     *  This endpoint returns a list of all users in the database. It is just for development purposes.
     * @return returns a list with all the users.
     */
    @PostMapping("table-status")
    public String tableStatus(@RequestBody TableStatusInfo info) { 
        TableStatusResponse tableStatus = this.tableManager.tableStatus(info);
        return this.gson.toJson(tableStatus);
    }
    
    /**
     * This endpoint receives a session token and returns the user associated with it. It is just for development purposes.
     * @param sessionToken
     * @return returns a {@link User} in Json format or null if the session token is not valid
     */
    @GetMapping("sessionstatus")
    public String sessionStatus(@RequestParam String sessionToken) {
        this.log.info("Access session status with sessionToken: " + sessionToken);
        User user = this.model.getUserByToken(sessionToken);
        if (user == null)
            return "null";
        return this.gson.toJson(user);
    }

    @GetMapping("hello")
    public String hello() { 
        this.log.info("Someone says hello :)");
        String result = "hello";
        return result;
    }   
}