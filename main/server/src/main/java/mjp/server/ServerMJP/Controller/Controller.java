/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package mjp.server.ServerMJP.Controller;

import mjp.server.responseData.UserResponse;
import com.google.gson.Gson;
import mjp.server.ServerMJP.database.Customer;
import mjp.server.ServerMJP.database.CustomerRepository;
import java.util.ArrayList;
//import database.Customer;
//import database.CustomerRepository;
import mjp.server.ServerMJP.ServerMjpApplication;
import mjp.server.ServerMJP.database.AplicationUser;
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
import mjp.server.ServerMJP.database.AplicationUserRepository;



/**
 *
 * @author Joan Renu Valls
 */

@RestController
public class Controller {
    boolean userLogged = false;
    boolean adminLogged = false;
    private Gson gson;
        private CustomerRepository repository;
        final Logger log = LoggerFactory.getLogger(ServerMjpApplication.class);
        
        
        private AplicationUserRepository userRepository;
    
    public Controller(){
        this.gson = new Gson();
        
    }
        
    public LoginResponse handleLogin(LoginInfo info) {
        System.out.println(String.format("Loggin attempt with username: %s, password: %s.", info.getUsername(), info.getPassword()));
        if(info.getUsername() == null || info.getPassword() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (info.getUsername().equals("Twiki") && info.getPassword().equals("Tuki")){
            userLogged = true;
            return new LoginResponse("1234", UserRole.USER);
        }
        if (info.getUsername().equals("Ping") && info.getPassword().equals("Pong")){
            adminLogged = true;
            return new LoginResponse("4321", UserRole.USER);
        }
        else    
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    
    public boolean handleLogout(LogoutInfo logoutInfo) {
        if (logoutInfo.getSessionToken() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        System.out.println("Logging out !!!!!  " + logoutInfo.getSessionToken());
        if (logoutInfo.getSessionToken().equals("1234"))
            this.userLogged = false;
        else if (logoutInfo.getSessionToken().equals("4321"))
            this.adminLogged = false;
        else    
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return true;
    }
        
    @GetMapping("/dobla")
    public int resultat(@RequestParam("enter") int enter) {
        return enter*2;
    }
    
        
    @GetMapping("allusers")
    public String allusers() {
        log.info("Users found with findAll()");
        ArrayList<AplicationUser> users = new ArrayList();
        String result = "";
        
        userRepository.findAll().forEach(user -> {
            log.info(user.toString());
            users.add(user);
        });
        for (AplicationUser user: users) {
            result += user.toString() + '\n';
        }
        return "users: " + result;
    }
    
    @GetMapping("customers")
    public String hello() {
        log.info("Customers found with findAll()");
        ArrayList<Customer> customers = new ArrayList();
        String result = "";
        
        repository.findAll().forEach(customer -> {
            log.info(customer.toString());
            customers.add(customer);
        });
        for (Customer customer: customers) {
            result += customer.toString() + '\n';
        }
        return result;
    }
    
    @GetMapping("/user")
    public String user(@RequestBody UserInfo userInfo){ 
        String error = userInfo.getDataError();
        if (error != null) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        String sessionToken = userInfo.getSessionToken();
        boolean logged = false;
        UserRole role = null;
        if (sessionToken.equals("1234")) {
            logged = this.userLogged;
            role = UserRole.USER;
        }
        else if (sessionToken.equals("4321")) {
            logged = this.adminLogged;
            role = UserRole.ADMIN;
        }
        UserResponse response = new UserResponse(logged, role);
        
        return this.gson.toJson(response) ;
    }
   
    
    @GetMapping(value = "/user", params = "username")
    public String user(@RequestParam String username){
        return String.format("<html> <body> <h1> Hellow %s </h1> </body> </html>", username);
        
    }
            
    
    @PostMapping("login")
    public String login(@RequestBody LoginInfo loginInfo){ 
        LoginResponse response = handleLogin(loginInfo);
        String responseJSON = this.gson.toJson(response);
        System.out.println("THE LOGIN RESPONSE IS: " + responseJSON);
        return this.gson.toJson(response);
    }
               
  
    @PostMapping("logout")
    public String logout(@RequestBody LogoutInfo logoutInfo){
        boolean result = handleLogout(logoutInfo);
        return this.gson.toJson(new LogoutResponse("Logged out"));
        
    }
    
    
    @Bean
    public CommandLineRunner controllerDemo(CustomerRepository repository) {
        this.repository = repository;
        return (args) -> {
           repository.save(new Customer("Jack", "Bauer")) ;
           repository.save(new Customer("Chloe", "O'Brian")) ;
           repository.save(new Customer("David", "Palmer")) ;

           System.out.println("CONTROLER DEMOOOOOOOOOOOOOOOOOOO");
        };
    }
    
        @Bean
        public CommandLineRunner userDemo(AplicationUserRepository repository) {
        this.userRepository = repository;
        return (args) -> {
            
           System.out.println("CONTROLER USER DEMOOOOOOOOOOOOOOOOOOO");
           AplicationUser user = new AplicationUser("Twiki", "Tuki", UserRole.ADMIN.getRole());
           repository.save(user) ;
           System.out.println("REGISTERED: " + user);
            user = new AplicationUser("Ping", "Pong", UserRole.USER.getRole());
           System.out.println("REGISTERED: " + user);
           repository.save(user) ;

        };
    }
    
}