/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.dataClasses.UserShift;
import mjp.server.queryData.LoginInfo;
import mjp.server.queryData.user.UserCreateInfo;
import mjp.server.queryData.user.UserDeleteInfo;
import mjp.server.queryData.user.UserGetInfo;
import mjp.server.queryData.user.UserUpdateInfo;
import mjp.server.responseData.LoginResponse;
import mjp.server.responseData.user.UserCreateResponse;
import mjp.server.responseData.user.UserDeleteResponse;
import mjp.server.responseData.user.UserGetResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import testUtils.TestDefault;
import testUtils.TestUtils;

/**
 *
 * @author Joan Renau Valls
 */
@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserManagementTest extends TestDefault {
    
    Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass
    private static String userSessionToken;
    private static String adminSessionToken;
    private static String newUserUsername = "Koy";
    private static String newUserPassword = "Yok";
    private static User newUser = new User("Koy", "Yok", UserRole.USER, "KoyName", "KoySurname", UserShift.FULL_TIME, LocalDate.of(2020, Month.DECEMBER, 12), LocalDate.of(2019, Month.SEPTEMBER, 20), "46257801I");
    private static User failUser = new User("Will", "Fail", UserRole.USER, "Willy", "Failly", UserShift.FULL_TIME, LocalDate.of(2020, Month.DECEMBER, 12), LocalDate.of(2015, Month.JUNE, 18), "76258891I");
    
    private static long newUserId;

    @LocalServerPort
    private int port;
    
    public int getPort() { return this.port; }
    
    @Autowired
    private TestRestTemplate restTemplate;
                     
    public TestRestTemplate getRestTemplate() { return this.restTemplate; }
    
//    TestUtils utils = (new TestUtils()).setPort("" + port).setRestTemplate(restTemplate);
    
//    public TestUtils getUtils() {return this.getUtils(); };
   
    @Test
    @Order(001)
    void setup() {
        printTestName("setting up");
        System.out.println("new user is: " + this.gson.toJson(newUser));
        System.out.println("fail user is: " + this.gson.toJson(failUser));
        
        String url = makeUrl("login");
        
        ResponseEntity<String> response = makePostRequest(url, new LoginInfo("Twiki", "Tuki"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        LoginResponse resObj = gson.fromJson(response.getBody(), LoginResponse.class);
        assertNotNull(resObj);
        userSessionToken = resObj.token;
        
        ResponseEntity<String> adminResponse = makePostRequest(url, new LoginInfo("Ping", "Pong"));
        assertThat(adminResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LoginResponse adminLoginResponse = gson.fromJson(adminResponse.getBody(), LoginResponse.class);
        adminSessionToken = adminLoginResponse.token;
        
        
        System.out.println(" user tests: user sessionToken = " + userSessionToken);
        url = makeUrl("/sessionstatus?sessionToken=" + userSessionToken);
        System.out.println("Making a request to " + url);
        
        String  response2 = this.restTemplate.getForObject(url, String.class);
        System.out.println("::::::::: ::::::::: Sessionstatus check returned: " + response2);
        
        User user = gson.fromJson(response2, User.class);
        assertNotNull(user.getId());  
    } 
    
    @Test
    @Order(100)
    void CreateUserWithoutSessionToken() {
        printTestName("CreateUserWithoutSessionToken");
        String url = makeUrl("/user/create");
//        User user = new User("Fake", "Faker", UserRole.USER);
        User user = failUser;
        UserCreateInfo request = new UserCreateInfo(null, user);
        ResponseEntity<String> response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    @Test
    @Order(150)
    void CreateUserWithoutUserInfo() {
        printTestName("CreateUserWithoutUserInfo");
        String url = makeUrl("/user/create");
        UserCreateInfo request = new UserCreateInfo(adminSessionToken, null);
        ResponseEntity<String> response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
        
    @Test
    @Order(200)
    void CreateUserWithInvalidSessionToken() {
        printTestName("CreateUserWithInvalidSessionToken");
        String url = makeUrl("/user/create");
//        User user = new User("Fake", "Faker", UserRole.USER);
        User user = failUser;
        UserCreateInfo request = new UserCreateInfo("invlid_session_token", user);
        ResponseEntity<String> response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
       
    @Test
    @Order(300)
    void CreateUserTwiceTest() {
        printTestName("CreateUserTwiceTest");
        String url = makeUrl("/user/create");
//        User user = new User(newUserUsername, newUserPassword, UserRole.USER);
        UserCreateInfo request = new UserCreateInfo(adminSessionToken, newUser);
        System.out.println("new user is: " + this.gson.toJson(newUser));
        System.out.println("fail user is: " + this.gson.toJson(failUser));
        System.out.println("request is: " + this.gson.toJson(request));
        ResponseEntity<String> response = makePostRequest(url, request);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserCreateResponse responseObject = this.gson.fromJson(response.getBody(), UserCreateResponse.class);
        assertNotNull(responseObject.getUser().getId());
        newUser = responseObject.getUser();
        assertThat(responseObject.getUser().getUsername()).isEqualTo(newUserUsername);
        
        response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
    }
           
    @Test
    @Order(400)
    void CreateUserAsNormalUserTest() {
        printTestName("CreateUserAsNormalUserTest");
        String url = makeUrl("/user/create");
//        User user = new User("Will", "Fail", UserRole.USER);
        User user = failUser;
        UserCreateInfo request = new UserCreateInfo(userSessionToken, user);
        ResponseEntity<String> response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    @Order(500)
    void getUserWithNoSessionToken(){
        printTestName("getUserWithNoSessionToken");
        String url = makeUrl("/user/get");
        UserGetInfo requestInfo = new UserGetInfo(null, "Twiki");
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    @Test
    @Order(600)
    void getUserWithInvalidSessionToken(){
        printTestName("getUserWithInvalidSessionToken");
        String url = makeUrl("/user/get");
        UserGetInfo requestInfo = new UserGetInfo("invalid_session_token", "Twiki");
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Order(700)
    void GetAllUsers() {
        printTestName("GetAllUsers");
        
        String url = makeUrl("/user/get");
        UserGetInfo requestInfo = new UserGetInfo(userSessionToken);
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserGetResponse usersList = gson.fromJson(response.getBody(), UserGetResponse.class);
        assertThat(usersList.getUser().size()).isEqualTo(3);
        ArrayList<String> CurrentUsers = new ArrayList(List.of("Twiki", "Ping", newUserUsername));
        for (User user : usersList.getUser()){
            assertThat(CurrentUsers.remove(user.getUsername())).isEqualTo(true);
        }
    }
    
    @Test
    @Order(800)
    void getOneUserByUsername() {
        printTestName("getOneUserByUsername");
        
        String url = makeUrl("/user/get");
        UserGetInfo requestInfo = new UserGetInfo(userSessionToken, newUserUsername);
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserGetResponse usersList = this.gson.fromJson(response.getBody(), UserGetResponse.class);
        assertThat(usersList.getUser().size()).isEqualTo(1);
        assertThat(usersList.getUser().get(0).getUsername()).isEqualTo(newUserUsername);
    }
    
    @Test
    @Order(850)
    void getOneUserById() {
        printTestName("getOneUserByUsername");
        
        String url = makeUrl("/user/get");
        UserGetInfo requestInfo = new UserGetInfo(userSessionToken, newUser.getId());
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserGetResponse usersList = this.gson.fromJson(response.getBody(), UserGetResponse.class);
        assertThat(usersList.getUser().size()).isEqualTo(1);
        assertThat(usersList.getUser().get(0).getUsername()).isEqualTo(newUserUsername);
    }
      
    @Test
    @Order(900)
    void getNoExisitngUser() {
        printTestName("getNoExisitngUser");
        
        String url = makeUrl("/user/get");
        UserGetInfo requestInfo = new UserGetInfo(userSessionToken, "I_do_not_exist");
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    
    @Test
    @Order(1000)
    void UpdateUserWithoutSessionToken() {
        printTestName("UpdateUserWithoutSessionToken");
        String url = makeUrl("/user/update");
//        User user = new User("Fake", "Faker", UserRole.USER);
        User user = failUser;
        UserUpdateInfo request = new UserUpdateInfo(null, user);
        ResponseEntity<String> response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    @Test
    @Order(1100)
    void UpdateUserWithoutUserInfo() {
        printTestName("UpdateUserWithoutUserInfo");
        String url = makeUrl("/user/update");
//        User user = new User("Fake", "Faker", UserRole.USER);
        User user = failUser;
        UserUpdateInfo request = new UserUpdateInfo(adminSessionToken, null);
        ResponseEntity<String> response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
        
    @Test
    @Order(1200)
    void UpdateUserWithInvalidSessionToken() {
        printTestName("UpdateUserWithInvalidSessionToken");
        String url = makeUrl("/user/update");
//        User user = new User("Fake", "Faker", UserRole.USER);
        User user = failUser;
        UserUpdateInfo request = new UserUpdateInfo("invlid_session_token", user);
        ResponseEntity<String> response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
       
    @Test
    @Order(1300)
    void UpdateUserTest() {
        printTestName("UpdateUserTest");
        String url = makeUrl("/user/update");
//        newUserUsername = "YokUpdated";
//        newUserPassword = "KoyUpdated";
//        User user = new User(newUserId, newUserUsername, newUserPassword, UserRole.USER);
        newUser.setUsername("YokUpdated");
        newUser.setPassword("KoyUpdated");
        User user = newUser;
        UserUpdateInfo request = new UserUpdateInfo(adminSessionToken, user);
        ResponseEntity<String> response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
           
    @Test
    @Order(1400)
    void UpdateUserAsNormalUserTest() {
        printTestName("UpdateUserAsNormalUserTest");
        String url = makeUrl("/user/create");
//        User user = new User("Will", "Fail", UserRole.USER);
        User user = failUser;
        UserUpdateInfo request = new UserUpdateInfo(userSessionToken, user);
        ResponseEntity<String> response = makePostRequest(url, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
             
    @Test
    @Order(1900)
    void deteteUserWitNoSessionToken(){
        printTestName("deteteUserWitNoSessionToken");
        
        String url = makeUrl("/user/delete");
        UserGetInfo requestInfo = new UserGetInfo(null, newUser.getUsername());
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    @Test
    @Order(2000)
    void deleteUserAsNormalUser() {
        printTestName("deleteUser");
        
        String url = makeUrl("/user/delete");
        UserGetInfo requestInfo = new UserGetInfo(userSessionToken, newUser.getUsername());
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
       
    @Test
    @Order(2100)
    void deleteNoExistingUser() {
        printTestName("deleteNoExistingUser");
        
        String url = makeUrl("/user/delete");
        UserGetInfo requestInfo = new UserGetInfo(adminSessionToken, "I_do_not_exist");
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }  
   
        
    @Test
    @Order(2200)
    void deleteExistingUser() {
        printTestName("GetNoExisitngUser");
        String usernameToDelete = newUser.getUsername();
        
        String url = makeUrl("/user/delete");
        UserDeleteInfo requestInfo = new UserDeleteInfo(adminSessionToken, usernameToDelete);
        ResponseEntity<String> response = makePostRequest(url, requestInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserDeleteResponse responseObject = this.gson.fromJson(response.getBody(), UserDeleteResponse.class);
        assertThat(responseObject.getMessage()).isEqualTo("success");
    
        UserGetInfo requestCheckInfo = new UserGetInfo(adminSessionToken, usernameToDelete);
        ResponseEntity<String> responseCheckInfo = makePostRequest(url, requestCheckInfo);
        assertThat(responseCheckInfo.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
