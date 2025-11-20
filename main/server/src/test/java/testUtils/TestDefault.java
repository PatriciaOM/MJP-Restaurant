/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.User;
import mjp.server.TestData;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.LoginInfo;
import mjp.server.queryData.sessionService.SessionServiceCreateInfo;
import mjp.server.queryData.table.TableCreateInfo;
import mjp.server.queryData.table.TableGetInfo;
import mjp.server.queryData.user.UserUpdateInfo;
import mjp.server.responseData.LoginResponse;
import mjp.server.responseData.sessionService.SessionServiceCreateResponse;
import mjp.server.responseData.table.TableCreateResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import mjp.server.uitls.serializers.LocalDateTimeAdapter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    public static TestData defaultData = new TestData();
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
    
    public void setDefaultDataLogins() {
        Credentials user = defaultData.userCredentials;
        user.setSessionToken(this.login(user.getUsername(), user.getPassword()));
        Credentials admin = defaultData.adminCredentials;
        admin.setSessionToken(this.login(admin.getUsername(), admin.getPassword()));
    }
    
//    public void refreshDefaultDataTables() {
//        defaultData.initTablesData();
//        for (int i = 0; i < defaultData.allTables.size(); i++) {
//            TableRestaurant table = defaultData.allTables.get(i);
//            assertNotNull(table.getId());
//            TableGetInfo info = new TableGetInfo(defaultData.adminCredentials.getSessionToken(), table.getId());
//            ResponseEntity<String> response = makePostRequest("/table/get", info);
//            System.out.println(String.format("%s: Asked for table %s, and got %s", i, table.getId(), response.getBody()));
//            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//            TableRestaurant newTable = gson.fromJson(response.getBody(), TableRestaurant.class);
//            defaultData.allTables.set(i, table);
//        }
//    }
    
    public void createDefaultDataTables() {
//        defaultData.initTablesData();
        String url = makeUrl("/table/create");
        for (int i = 0; i < defaultData.allTables.size(); i++) {
            TableRestaurant table = defaultData.allTables.get(i);
            String sessionToken = defaultData.adminCredentials.getSessionToken();
            assertNotNull(sessionToken);
            assertNotNull(table);
            table.setId(null);
            assertNull(table.getId());
            TableCreateInfo info = new TableCreateInfo(sessionToken, table);

            ResponseEntity<String> response = makePostRequest(url, info);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//            if (response.getStatusCode() == HttpStatus.OK) {
            TableCreateResponse responseObject = gson.fromJson(response.getBody(), TableCreateResponse.class);
            TableRestaurant createdTable = responseObject.getTable();
            assertNotNull(createdTable.getId());
            defaultData.allTables.set(i, createdTable);
//            }
        }
        defaultData.refreshTables();
        assertNotNull(defaultData.allTables.get(0).getId());
    }
    
    public void creteDefaultDataSessionService(){
        assertNotNull(defaultData.allTables.get(0).getId());
        assertNotNull(defaultData.initialTable.getId());
//        defaultData.initTablesData();
        String url = makeUrl("/session-service/create");
            System.out.println("Creating sessionService::::::::::::::: " );
        for (int i = 0; i < defaultData.allSessionService.size(); i++) {
            System.out.println("Creating sessionService: " + i);
            SessionService sessionService = defaultData.allSessionService.get(i);
            String sessionToken = defaultData.adminCredentials.getSessionToken();
            assertNotNull(sessionToken);
            assertNotNull(sessionService);
//            sessionService.setId(null);   //This should be redundant
            assertNull(sessionService.getId()); // Get shulre the line above is redundant
            assertNotNull(sessionService.getIdTable()); 
            SessionServiceCreateInfo info = new SessionServiceCreateInfo(sessionToken, sessionService);
            ResponseEntity<String> response = makePostRequest(url, info);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            SessionServiceCreateResponse responseObject = gson.fromJson(response.getBody(), SessionServiceCreateResponse.class);
            List<SessionService> createdSessionServcie = responseObject.getMessageData();
            assertThat(createdSessionServcie.size()).isEqualTo(1);
            assertNotNull(createdSessionServcie.get(0).getId());
            defaultData.allSessionService.set(i, createdSessionServcie.get(0));
        }
        defaultData.refreshSessionService();
    }
    
   

}
