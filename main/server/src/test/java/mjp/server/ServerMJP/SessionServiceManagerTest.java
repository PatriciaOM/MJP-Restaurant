/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.ServerMJP.database.SessionService.SessionServiceStatus;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.sessionService.SessionServiceCreateInfo;
import mjp.server.queryData.sessionService.SessionServiceDeleteInfo;
import mjp.server.queryData.sessionService.SessionServiceGetInfo;
import mjp.server.queryData.sessionService.SessionServiceUpdateInfo;
import mjp.server.responseData.sessionService.SessionServiceCreateResponse;
import mjp.server.responseData.sessionService.SessionServiceDeleteResponse;
import mjp.server.responseData.sessionService.SessionServiceGetResponse;
import mjp.server.responseData.sessionService.SessionServiceResponse;
import mjp.server.responseData.sessionService.SessionServiceUpdateResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import mjp.server.uitls.serializers.LocalDateTimeAdapter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import testUtils.Credentials;
import testUtils.TestDefaultCrud;


@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class SessionServiceManagerTest extends TestDefaultCrud<
        Long,
        SessionService,
        SessionServiceCreateResponse,
        SessionServiceGetResponse,
        SessionServiceUpdateResponse,
        SessionServiceDeleteResponse
    > {
    private static String resourceUri = "/session-service";
    static Credentials user = new Credentials("Twiki", "Tuki", null);
    static Credentials admin = new Credentials("Ping", "Pong", null);
    
    private static String userSessionToken;
    private static String adminSessionToken;
//    Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass
    
    private Gson gson = (new GsonBuilder())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    
    @LocalServerPort
    private int port;
    public int getPort() { return this.port; }
    
    @Autowired
    private TestRestTemplate restTemplate;
    public TestRestTemplate getRestTemplate() { return this.restTemplate; }
    
    public String getResourceUri() {return this.resourceUri;}
            
    @Override
    protected Credentials getUserCredentials() {
        return this.user;
    }

    @Override
    protected Credentials getAdminCredentials() {
        return this.admin;
    }
 
    @Override
    protected SessionService getInitialItem() {
        return defaultData.initialSessionService;
    }

    @Override
    protected SessionService getUpdatedItem() {
        return defaultData.updatedSessionService;
    }

    @Override
    protected List<SessionService> getAllTestItems() {
        return defaultData.allSessionService;
    }
    
    @Override
    protected SessionService getNoExistingItem() {
        return defaultData.noExsistingSessionService;
    }  
    
    @Override
    public AuthorizedQueryInfo<SessionService> generateCreateRequest(String sessionToken, SessionService entry) {
        return new SessionServiceCreateInfo(sessionToken, entry);
    }

    @Override
    public AuthorizedQueryInfo<Long> generateGetRequest(String sessionToken, Long entryId) {
        return new SessionServiceGetInfo(sessionToken, entryId);
    }
    
    @Override
    public AuthorizedQueryInfo<SessionService> generateUpdateRequest(String sessionToken, SessionService entry) {
        return new SessionServiceUpdateInfo(sessionToken, entry);
    }    
     
    @Override
    public AuthorizedQueryInfo<Long> generateDeleteRequest(String sessionToken, Long entryId) {
        return new SessionServiceDeleteInfo(sessionToken, entryId);
    }
    
    @Override
    public Class<SessionServiceCreateResponse> getCreateResponseClass() {
        return SessionServiceCreateResponse.class;
    }

    @Override
    public Class<SessionServiceGetResponse> getGetResponseClass() {
        return SessionServiceGetResponse.class;
    }

    @Override
    public Class<SessionServiceUpdateResponse> getUpdateResponseClass() {
        return SessionServiceUpdateResponse.class;
    }

    @Override
    public Class<SessionServiceDeleteResponse> getDeleteResponseClass() {
        return SessionServiceDeleteResponse.class;
    }
    

    @Override
    public Credentials getCreateCredentials(){
        return this.getUserCredentials();
    }

    @Override
    public Credentials getGetCredentials(){
        return this.getUserCredentials();
    }

    @Override
    public Credentials getUpdateCredentials(){
        return this.getUserCredentials();
    }

    @Override
    public Credentials getDeleteCredentials(){
        return this.getUserCredentials();
    }
    
    @Override
    public String getClassName() {
        return "SessionService";
    }
       
    @Test
    @Order(001)
    void setup(){
        basicSetup("setup");
        
        setDefaultDataLogins();  
        
        defaultData.initTablesData();
        createDefaultDataTables();
        defaultData.initSessionServicesData();        
//        creteDefaultDataSessionService();
        
        
        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
        userSessionToken = this.getUserCredentials().getSessionToken(); // TODO delete
        adminSessionToken = this.getAdminCredentials().getSessionToken(); // TODO delete
    }
    
    @Test
    @Order(200)
    void createSessionServiceBasicTests(){
        createItemBasicTests("create" + getClassName() + "BasicTests", getAdminCredentials().getSessionToken());
    }
    
    @Test
    @Order(300)
    void createAllSessionService(){
        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
        this.createAllItems("createAll" + getClassName());
    }
    
    @Test
    @Order(400)
    void getSessionServicesBasicTests(){
        getItemBasicTests("get" + getClassName() + "BasicTests", getUserCredentials().getSessionToken());
    }
  
    @Test
    @Order(500)
    void getSessionServcieById(){
        getItemById("get" + getClassName() + "ById");
    }
      
    @Test
    @Order(550)
    void getNoExistingSessionServcieById(){
        getNoExistingItemById("getNoExisting" + getClassName() + "ById");
    }
    
    @Test
    @Order(600)
    void updateSessionServcieBasicTests(){
        updateItemBasicTests("create" + getClassName() + "BasicTests", getAdminCredentials().getSessionToken());
    }
    
    @Test
    @Order(700)
    void updateSessionServcie(){
        this.updateItem("update" + getClassName());       
    }
    
    @Test
    @Order(800)
    void deleteSessionServcieBasicTests(){
        deleteItemBasicTests("delete" + getClassName() + "BasicTests", getAdminCredentials().getSessionToken());
    }
     @Test
    @Order(850)
    void getToDeletSessionServcie(){
        getItemToDelete("getToDele" + getClassName());
    }
           
    @Test
    @Order(900)
    void deleteSessionServcie(){
        deleteItem("delete"+ getClassName());
    }
          
    @Test
    @Order(1000)
    void getDeletedSessionServcie(){
        getDeletedItem("getDeleted" + getClassName());
    }
    
    
    /** OTHER TESTS **/
    
    @Test
    @Order(2000)
    void CreateTwoUnpaidSessionServiceOnTheSameTable() {
        getDeletedItem("CreateTwoUnpaidSessionServiceOnTheSameTable" + getClassName());
        SessionServiceCreateInfo info;
        ResponseEntity<String> response;
        
        SessionService sessionService = new SessionService(defaultData.initialSessionService);
        sessionService.setStatus(SessionService.SessionServiceStatus.OPEN);
        sessionService.setId(null);
        sessionService.setIdTable(defaultData.mockTable2.getId());
        
        assertNotNull(defaultData.userCredentials.getSessionToken());
        assertNotNull(sessionService);
        info = new SessionServiceCreateInfo(defaultData.userCredentials.getSessionToken(), sessionService);
        response = makePostRequest("/session-service/create", info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        sessionService.setStatus(SessionService.SessionServiceStatus.CLOSED);
        info = new SessionServiceCreateInfo(defaultData.userCredentials.getSessionToken(), sessionService);
        response = makePostRequest("/session-service/create", info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
    }
    
        @Test
    @Order(2000)
    void UpdateToTwoUnpaidSessoinServiceOnTheSameTable() {
        getDeletedItem("UpdateToTwoUnpaidSessoinServiceOnTheSameTable" + getClassName());
        
        SessionServiceCreateInfo info;
        ResponseEntity<String> response;
        
        SessionService sessionService = new SessionService(defaultData.initialSessionService);
        sessionService.setStatus(SessionService.SessionServiceStatus.PAID);
        sessionService.setId(null);
        sessionService.setIdTable(defaultData.mockTable2.getId());
        
        assertNotNull(defaultData.userCredentials.getSessionToken());
        assertNotNull(sessionService);
        info = new SessionServiceCreateInfo(defaultData.userCredentials.getSessionToken(), sessionService);
        response = makePostRequest("/session-service/create", info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        SessionServiceResponse responseObject = gson.fromJson(response.getBody(), SessionServiceResponse.class);
        
        sessionService.setId(responseObject.getSessionServices().get(0).getId());
        sessionService.setStatus(SessionServiceStatus.CLOSED);
        SessionServiceUpdateInfo infoUpdate = new SessionServiceUpdateInfo(defaultData.userCredentials.getSessionToken(), sessionService);
        response = makePostRequest("/session-service/update", infoUpdate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
//        SessionServiceResponse responseObject = gson.fromJson(response.getBody(), SessionServiceResponse.class);
        
        
//        sessionService.setStatus(SessionService.SessionServiceStatus.CLOSED);
//        info = new SessionServiceUpdateInfo(defaultData.userCredentials.getSessionToken(), sessionService);
//        response = makePostRequest("/session-service/create", info);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
    }
}