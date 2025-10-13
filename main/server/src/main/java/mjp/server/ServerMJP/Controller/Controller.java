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
import mjp.server.queryData.UserInfo;
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
 *
 * @author Joan Renu Valls
 */

@RestController
public class Controller {
    boolean userLogged = false;
    boolean adminLogged = false;
    private Gson gson;
    private Model model;
    private SessionManager sessionManager = new SessionManager();
    final Logger log = LoggerFactory.getLogger(ServerMjpApplication.class);
    
    public Controller(
        Model model,
        UserRepository aplicationRepository
    ){
        this.gson = new Gson();
        this.model = model;
        this.model.mockData();
    }
    
    @PostMapping("login")
    public String login(@RequestBody LoginInfo loginInfo){ 
        LoginResponse response = this.model.login(loginInfo);
        String responseJSON = this.gson.toJson(response);
        return this.gson.toJson(response);
    }   
  
    @PostMapping("logout")
    public String logout(@RequestBody LogoutInfo logoutInfo){
        LogoutResponse response = this.model.logout(logoutInfo);
        return this.gson.toJson(response);
    }
    
    @GetMapping("sessionstatus")
    public String sessionStatus(@RequestParam String session) {
        User user = this.model.getUserByToken(session);
        if (user == null)
            return "null";
        return this.gson.toJson(user);
    }
            
    @GetMapping("allusers")
    public String allusers() {
        String users = this.model.allUsers();
//        log.info(users);
        return users;
    }
}