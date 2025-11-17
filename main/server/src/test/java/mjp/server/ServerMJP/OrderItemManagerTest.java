/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mjp.server.ServerMJP.database.OrderItem;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.orderItem.OrderItemCreateInfo;
import mjp.server.queryData.orderItem.OrderItemDeleteInfo;
import mjp.server.queryData.orderItem.OrderItemGetInfo;
import mjp.server.queryData.orderItem.OrderItemUpdateInfo;
import mjp.server.responseData.orderItem.OrderItemCreateResponse;
import mjp.server.responseData.orderItem.OrderItemDeleteResponse;
import mjp.server.responseData.orderItem.OrderItemGetResponse;
import mjp.server.responseData.orderItem.OrderItemUpdateResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import mjp.server.uitls.serializers.LocalDateTimeAdapter;
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
public class OrderItemManagerTest extends TestDefaultCrud<
        Long,
        OrderItem,
        OrderItemCreateResponse,
        OrderItemGetResponse,
        OrderItemUpdateResponse,
        OrderItemDeleteResponse
    > {
    private static String resourceUri = "/order-item";
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
    
    static OrderItem initialSessionService = new OrderItem(
    );
      
    static OrderItem mockSessionService1 = new OrderItem(
    );    
    
    static OrderItem mockSessionService2 = new OrderItem(
    );  
    
    static OrderItem mockSessionService3 = new OrderItem(
    );
    
    
    List<OrderItem> allItems = List.of(initialSessionService, mockSessionService1, mockSessionService2, mockSessionService3);
    
    static OrderItem noExsistingSessionService = new OrderItem(
    );
    
    static OrderItem updatedSessionService = new OrderItem(
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
    protected OrderItem getInitialItem() {
        return initialSessionService;
    }

    @Override
    protected OrderItem getUpdatedItem() {
        return updatedSessionService;
    }

    @Override
    protected List<OrderItem> getAllTestItems() {
        return this.allItems;
    }
    
    @Override
    protected OrderItem getNoExistingItem() {
        return noExsistingSessionService;
    }  
    
    @Override
    public AuthorizedQueryInfo<OrderItem> generateCreateRequest(String sessionToken, OrderItem entry) {
        return new OrderItemCreateInfo(sessionToken, entry);
    }

    @Override
    public AuthorizedQueryInfo<Long> generateGetRequest(String sessionToken, Long entryId) {
        return new OrderItemGetInfo(sessionToken, entryId);
    }
    
    @Override
    public AuthorizedQueryInfo<OrderItem> generateUpdateRequest(String sessionToken, OrderItem entry) {
        return new OrderItemUpdateInfo(sessionToken, entry);
    }    
     
    @Override
    public AuthorizedQueryInfo<Long> generateDeleteRequest(String sessionToken, Long entryId) {
        return new OrderItemDeleteInfo(sessionToken, entryId);
    }
    
    @Override
    public Class<OrderItemCreateResponse> getCreateResponseClass() {
        return OrderItemCreateResponse.class;
    }

    @Override
    public Class<OrderItemGetResponse> getGetResponseClass() {
        return OrderItemGetResponse.class;
    }

    @Override
    public Class<OrderItemUpdateResponse> getUpdateResponseClass() {
        return OrderItemUpdateResponse.class;
    }

    @Override
    public Class<OrderItemDeleteResponse> getDeleteResponseClass() {
        return OrderItemDeleteResponse.class;
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
        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
        userSessionToken = this.getUserCredentials().getSessionToken(); // TODO delete
        adminSessionToken = this.getAdminCredentials().getSessionToken(); // TODO delete
    }
    
    @Test
    @Order(200)
    void createOrderItemBasicTests(){
        createItemBasicTests("create" + getClassName() + "BasicTests", getAdminCredentials().getSessionToken());
    }
    
    @Test
    @Order(300)
    void createAllOrderItem(){
        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
        this.createAllItems("createAll" + getClassName());
    }
    
    @Test
    @Order(400)
    void getOrderItemBasicTests(){
        getItemBasicTests("get" + getClassName() + "BasicTests", getUserCredentials().getSessionToken());
    }
  
    @Test
    @Order(500)
    void getOrderItemById(){
        getItemById("get" + getClassName() + "ById");
    }
      
    @Test
    @Order(550)
    void getNoExistingSessionServcieById(){
        getNoExistingItemById("getNoExisting" + getClassName() + "ById");
    }
    
    @Test
    @Order(600)
    void updateOrderItemBasicTests(){
        updateItemBasicTests("create" + getClassName() + "BasicTests", getAdminCredentials().getSessionToken());
    }
    
    @Test
    @Order(700)
    void updateOrderItem(){
        this.updateItem("update" + getClassName());       
    }
    
    @Test
    @Order(800)
    void deleteOrderItemBasicTests(){
        deleteItemBasicTests("delete" + getClassName() + "BasicTests", getAdminCredentials().getSessionToken());
    }
     @Test
    @Order(850)
    void getToDeletOrderItem(){
        getItemToDelete("getToDele" + getClassName());
    }
           
    @Test
    @Order(900)
    void deleteOrderItem(){
        deleteItem("delete"+ getClassName());
    }
          
    @Test
    @Order(1000)
    void getDeletedOrderItem(){
        getDeletedItem("getDeleted" + getClassName());
    }

     
}