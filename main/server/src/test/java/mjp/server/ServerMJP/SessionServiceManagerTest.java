/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.sessionService.SessionServiceCreateInfo;
import mjp.server.queryData.sessionService.SessionServiceDeleteInfo;
import mjp.server.queryData.sessionService.SessionServiceGetInfo;
import mjp.server.queryData.sessionService.SessionServiceUpdateInfo;
import mjp.server.responseData.sessionService.SessionServiceCreateResponse;
import mjp.server.responseData.sessionService.SessionServiceDeleteResponse;
import mjp.server.responseData.sessionService.SessionServiceGetResponse;
import mjp.server.responseData.sessionService.SessionServiceUpdateResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
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
import testUtils.Credentials;
import testUtils.TestDefaultCrud;


@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass
    
    @LocalServerPort
    private int port;
    public int getPort() { return this.port; }
    
    @Autowired
    private TestRestTemplate restTemplate;
    public TestRestTemplate getRestTemplate() { return this.restTemplate; }
    
    public String getResourceUri() {return this.resourceUri;}
    
    static SessionService initialSessionService = new SessionService(
        1L,
        0,
        4,
        1,
        2,
        LocalDate.of(2020, Month.DECEMBER, 12),
        LocalDate.of(2020, Month.DECEMBER, 12),
        SessionService.SessionServiceStatus.CLOSED,
        5,
            ""
    );
      
    static SessionService mockSessionService1 = new SessionService(
        1L,
        1,
        4,
        1,
        2,
        LocalDate.of(2020, Month.DECEMBER, 13),
        LocalDate.of(2020, Month.DECEMBER, 13),
        SessionService.SessionServiceStatus.OPEN,
        5,
        "Very Nice place"
    );    
    
    static SessionService mockSessionService2 = new SessionService(
        1L,
        1,
        4,
        1,
        2,
        LocalDate.of(2020, Month.DECEMBER, 14),
        LocalDate.of(2020, Month.DECEMBER, 14),
        SessionService.SessionServiceStatus.PAID,
        5,
        "Very Nicer place"
    );  
    
    static SessionService mockSessionService3 = new SessionService(
        1L,
        1,
        4,
        1,
        2,
        LocalDate.of(2020, Month.DECEMBER, 15),
        LocalDate.of(2020, Month.DECEMBER, 15),
        SessionService.SessionServiceStatus.OPEN,
        5,
        "Very Nicer placer"
    );
    
    
    List<SessionService> allItems = List.of(initialSessionService, mockSessionService1, mockSessionService2, mockSessionService3);
    
    static SessionService noExsistingSessionService = new SessionService(
        500000L, 
        5L, 
        3,
        4,
        1,
        2,
        LocalDate.of(2020, Month.DECEMBER, 15),
        LocalDate.of(2020, Month.DECEMBER, 15),
        SessionService.SessionServiceStatus.PAID,
        5,
        "Very Nicer placer"
    );
    
    static SessionService updatedSessionService = new SessionService(
        1L,
        0,
        4,
        1,
        2,
        LocalDate.of(2020, Month.DECEMBER, 12),
        LocalDate.of(2020, Month.DECEMBER, 12),
        SessionService.SessionServiceStatus.PAID,
        5,
        "Nice Place"
    );
        
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
        return initialSessionService;
    }

    @Override
    protected SessionService getUpdatedItem() {
        return updatedSessionService;
    }

    @Override
    protected List<SessionService> getAllTestItems() {
        return this.allItems;
    }
    
    @Override
    protected SessionService getNoExistingItem() {
        return noExsistingSessionService;
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
    public String getClassName() {
        return "SessionService";
    }
       
    @Test
    @Order(001)
    void setup(){
        basicSetup("setup");
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

     
}