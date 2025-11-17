/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.LoginInfo;
import mjp.server.queryData.user.UserUpdateInfo;
import mjp.server.responseData.LoginResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import mjp.server.uitls.serializers.LocalDateTimeAdapter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author  Joan Renau Valls
 */
public abstract class TestDefault {
//    protected class Credentials {
//        String username;
//        String password;
//        String sessionToken;
//
//        public Credentials(String username, String password, String sessionToken) {
//            this.username = username;
//            this.password = password;
//            this.sessionToken = sessionToken;
//        }
//
//        public String getUsername() {return username;}
//
//        public String getPassword() {return password;}
//
//        public String getSessionToken() {return sessionToken;}
//
//        public void setUsername(String username) {this.username = username;}
//
//        public void setPassword(String password) {this.password = password;}
//
//        public void setSessionToken(String sessionToken) {this.sessionToken = sessionToken;}
//        
//        
//    }
    
    private static final String RESET = "\033[0m";
    private static final String CYAN = "\033[36m";
    
    private Gson gson = (new GsonBuilder())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    
    String PROTOCOL = "http";
    String HOST = "localhost";
    
//    public abstract TestUtils getUtils();
    public abstract TestRestTemplate getRestTemplate();
    public abstract int getPort();
    
    public <Request>  ResponseEntity<String> makePostRequest(String url, Request data){
        String jsonBody = gson.toJson(data);
        return makePostRequest(url, jsonBody);
    }    
    
    
    public ResponseEntity<String> makePostRequest(String url, String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("Sennding: " + jsonBody);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        
        ResponseEntity<String> response = getRestTemplate().postForEntity(url, request, String.class);
        return response;
        
    }
        
    public String makeUrl(String endpoint) {
        String ret =  String.format("%s://%s:%s/%s", this.PROTOCOL, this.HOST, getPort(), endpoint);
        System.out.println(ret);
        return ret;
    }
           
    public static void printTestName(String name) {
        System.out.println();
        System.out.println(CYAN + "::: TEST ::: "+ name + RESET);
    }
    
    public String login(String username, String password) {
        String url = makeUrl("login");
        ResponseEntity<String> response = makePostRequest(url, new LoginInfo(username, password));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        LoginResponse resObj = gson.fromJson(response.getBody(), LoginResponse.class);
        assertNotNull(resObj);
        return resObj.token;
    }
    
    
    public User getUserBySessionToken(String sessionToken) {
        String url = makeUrl("/sessionstatus?sessionToken=" + sessionToken);
        
        String  response2 = this.getRestTemplate().getForObject(url, String.class);
        
        User user = gson.fromJson(response2, User.class);
        assertNotNull(user.getId()); 
        return user;
    }
    
    /**
     * 
     * @param <MessageDataType>
     * @param testName The name of the test
     * @param endPoint  The endpoint to send the request
     * @param messageObject The wrapper with all the information to be sent, contains sessionToken and messageData
     * @param messageData   The object to be sent
     * @param sesionToken A valid session token
     */
    
    public <MessageDataType> void basicRequestTests(
            String testName,
            String url,
            AuthorizedQueryInfo messageObject,
            MessageDataType messageData,
            String sessionToken
    ) {
        ResponseEntity<String> response;
        
        printTestName(testName);
        
        messageObject.setSessionToken(sessionToken);
        
        printTestName(testName + " Whithout sessionToken");
        messageObject.setSessionToken(null);
        messageObject.setMessageData(messageData);
        response = makePostRequest(url, messageObject);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        
        printTestName(testName + " Whith invalid sessionToken");
        messageObject.setSessionToken("Invalid session token");
        messageObject.setMessageData(messageData);
        response = makePostRequest(url, messageObject);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        
        if (messageData != null) {
            printTestName(testName + " Whithout info");
            messageObject.setSessionToken(sessionToken);
            messageObject.setMessageData(null);
            response = makePostRequest(url, messageObject);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }
    
//    protected abstract Credentials getUserCredentials();
//    protected abstract Credentials getAdminCredentials();
//    
//    
//    
//    protected void basicSetup(String testname){
//        printTestName(testname);
//        Credentials userCred = getUserCredentials();
//        Credentials adminCred = getAdminCredentials();
//
//        getUserCredentials().setSessionToken(this.login(userCred.getUsername(), userCred.getPassword()));
//        getAdminCredentials().setSessionToken(this.login(adminCred.getUsername(), adminCred.getPassword()));
//        System.out.println("usersSessionToken=" + userCred.getSessionToken());
//        System.out.println("adminSessionToken=" + adminCred.getSessionToken());
//        User user = this.getUserBySessionToken(userCred.getSessionToken());
//        User admin = this.getUserBySessionToken(adminCred.getSessionToken());
//        System.out.println("User user to json: " + gson.toJson(user));
//        System.out.println("Admin user to json: " + gson.toJson(admin));
//        assertThat(user.getRole()).isEqualTo(UserRole.USER);
//        assertThat(admin.getRole()).isEqualTo(UserRole.ADMIN);
//    }
}
