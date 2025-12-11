/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import mjp.server.ServerMJP.database.User;
import mjp.server.ServerMJP.database.UserRepository;
import mjp.server.dataClasses.UserRole;
import mjp.server.dataClasses.UserShift;
import mjp.server.queryData.user.UserCreateInfo;
import mjp.server.queryData.user.UserDeleteInfo;
import mjp.server.queryData.user.UserGetInfo;
import mjp.server.queryData.user.UserUpdateInfo;
import mjp.server.responseData.user.UserCreateResponse;
import mjp.server.responseData.user.UserDeleteResponse;
import mjp.server.responseData.user.UserGetResponse;
import mjp.server.responseData.user.UserUpdateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import mjp.server.uitls.Utils;

/**
 * Class for handling the requests and responses of the User class. Just gets the deserialized request objects and generates the response objects that will be serialized and returned.
 * @author Joan Renau Valls
 */
@Component
public class UserManager {
    private UserRepository userRepository;
    private SessionManager sessionManager;
    
    
    public UserManager(
            UserRepository userRepository,
            SessionManager sessionManager
    ){
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
        try {
         this.mockData();
        } catch (Exception e) {
        }
        
    }
    
    /**
     * Used to handle a create request
     * @param info
     * @return 
     */
    public UserCreateResponse create(UserCreateInfo info) {
        System.out.println("Creating usere with credential sessionToken: " + info.getSessionToken());
        System.out.println("The user is: " + info.getUser());
        if (info.getSessionToken() == null || info.getUser() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST );
        if (!this.sessionManager.validateAdminToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED );
        if (!this.userRepository.findByUsername(info.getUser().getUsername()).isEmpty())
            throw new ResponseStatusException(HttpStatus.LOCKED);
        info.getUser().setPassword(Utils.encrypt(info.getUser().getPassword()));
        this.userRepository.save(info.getUser());
        return new UserCreateResponse(info.getUser());
    }
    
    /**
     * Used to handle a get request
     * @param info
     * @return 
     */
    public UserGetResponse get(UserGetInfo info){
        if (info.getSessionToken() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!this.sessionManager.validateUserToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED );
//        if ((info.getUserName() == null)) && UserName() == null)
//        if (info.getSearchType() == UserGetInfo.SearchType.ALL)
        List<User> users;
        switch (info.getSearchType()){
            case ALL :
                return new UserGetResponse(this.allUsers());
            case BY_USERNAME :
                users = this.userRepository.findByUsername(info.getUserName());
                break;
            case BY_ID:
                users = List.of(this.userRepository.findById(info.getId()));
                break;
            default:
                throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        }
        if (users.size() != 1)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new UserGetResponse(users);
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
    /**
     * Used to handle a delete request
     * @param info
     * @return 
     */
    public UserDeleteResponse delete(UserDeleteInfo info) {
        if (info.getSessionToken() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); 
        if (!this.sessionManager.validateAdminToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        List<User> users = this.userRepository.findByUsername(info.getUserName());
        if (users.size() != 1)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.userRepository.delete(users.get(0));
        return new UserDeleteResponse();
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
    
    /**
     * Used to handle a update request
     * @param info
     * @return 
     */
    public UserUpdateResponse update(UserUpdateInfo info) {
        System.out.println("Updating usere with credential sessionToken: " + info.getSessionToken());
        System.out.println("The user is: " + info.getUser());
        if (info.getSessionToken() == null || info.getUser() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST );
        if (!this.sessionManager.validateAdminToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED );
        if (
            info.getUser().getId() == null ||
            this.userRepository.findById(info.getUser().getId()).isEmpty()
        ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.userRepository.save(info.getUser());
        return new UserUpdateResponse(info.getUser());
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        
    }
    
    private List<User> allUsers() {
        ArrayList<User> users = new ArrayList();
        this.userRepository.findAll().forEach(user -> {
            users.add(user);
        });
        return users;
      }


    private String allUsersString() {
        ArrayList<User> users = new ArrayList();
        String result = "";
        this.userRepository.findAll().forEach(user -> {
            users.add(user);
        });
        for (User user : users) {
            result += user.toString() + '\n';
        }
        return "users: " + result;
    }

    /**
     * Creates mock data for debug and development purposes.
     */
    public void mockData() {
        User user;
        user = new User("Twiki", Utils.encrypt("Tuki"), UserRole.USER, "Twiki", "Tuki", UserShift.MORNING, LocalDate.of(2017, Month.JANUARY, 9), LocalDate.of(2017, Month.MAY, 4), "46257891I");
        this.userRepository.save(user);
        user = new User("Ping",  Utils.encrypt("Pong"), UserRole.ADMIN, "Bota", "Rebota", UserShift.AFTERNOON, LocalDate.of(2017, Month.JANUARY, 9), LocalDate.of(2017, Month.OCTOBER, 4),"86657911I");
        this.userRepository.save(user);
    }

    
}
