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
import mjp.server.ServerMJP.database.Order;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.Order.OrderCreateInfo;
import mjp.server.queryData.Order.OrderDeleteInfo;
import mjp.server.queryData.Order.OrderGetInfo;
import mjp.server.queryData.Order.OrderUpdateInfo;
import mjp.server.responseData.Order.OrderCreateResponse;
import mjp.server.responseData.Order.OrderDeleteResponse;
import mjp.server.responseData.Order.OrderGetResponse;
import mjp.server.responseData.Order.OrderUpdateResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.MethodOrderer;
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
public class OrderManagerTest extends TestDefaultCrud<
        Long,
        Order,
        OrderCreateResponse,
        OrderGetResponse,
        OrderUpdateResponse,
        OrderDeleteResponse
    > {
    private static String resourceUri = "/order";
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
    
    static Order initialItem = new Order(
        1L,
        LocalDate.of(2020, Month.DECEMBER, 13),
        Order.Status.OPEN
    );
      
    static Order mockItem1 = new Order(
        1L,
        LocalDate.of(2020, Month.APRIL, 13),
        Order.Status.OPEN
    );    
    
    static Order mockItem2 = new Order(
        1L,
        LocalDate.of(2020, Month.DECEMBER, 12),
        Order.Status.OPEN
    );  
    
    static Order mockItem3 = new Order(
        1L,
        LocalDate.of(2020, Month.NOVEMBER, 13),
        Order.Status.OPEN
    );
    
    
    List<Order> allItems = List.of(initialItem, mockItem1, mockItem2, mockItem3);
    
    static Order noExsistingItem = new Order(
        5000L,
        1L,
        LocalDate.of(2020, Month.NOVEMBER, 13),
        Order.Status.OPEN
    );
    
    static Order updatedItem = new Order(
        1L,
        LocalDate.of(2020, Month.DECEMBER, 13),
        Order.Status.SERVED
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
    protected Order getInitialItem() {
        return initialItem;
    }

    @Override
    protected Order getUpdatedItem() {
        return updatedItem;
    }

    @Override
    protected List<Order> getAllTestItems() {
        return this.allItems;
    }
    
    @Override
    protected Order getNoExistingItem() {
        return noExsistingItem;
    }  
    
    @Override
    public AuthorizedQueryInfo<Order> generateCreateRequest(String sessionToken, Order entry) {
        return new OrderCreateInfo(sessionToken, entry);
    }

    @Override
    public AuthorizedQueryInfo<Long> generateGetRequest(String sessionToken, Long entryId) {
        return new OrderGetInfo(sessionToken, entryId);
    }
    
    @Override
    public AuthorizedQueryInfo<Order> generateUpdateRequest(String sessionToken, Order entry) {
        return new OrderUpdateInfo(sessionToken, entry);
    }    
     
    @Override
    public AuthorizedQueryInfo<Long> generateDeleteRequest(String sessionToken, Long entryId) {
        return new OrderDeleteInfo(sessionToken, entryId);
    }
    
    @Override
    public Class<OrderCreateResponse> getCreateResponseClass() {
        return OrderCreateResponse.class;
    }

    @Override
    public Class<OrderGetResponse> getGetResponseClass() {
        return OrderGetResponse.class;
    }

    @Override
    public Class<OrderUpdateResponse> getUpdateResponseClass() {
        return OrderUpdateResponse.class;
    }

    @Override
    public Class<OrderDeleteResponse> getDeleteResponseClass() {
        return OrderDeleteResponse.class;
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
        return "Order";
    }
       
    @Test
    @org.junit.jupiter.api.Order(001)
    void setup(){
        basicSetup("setup");
        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
        userSessionToken = this.getUserCredentials().getSessionToken(); // TODO delete
        adminSessionToken = this.getAdminCredentials().getSessionToken(); // TODO delete
    }
    
    @Test
    @org.junit.jupiter.api.Order(200)
    void createOrderBasicTests(){
        createItemBasicTests("create" + getClassName() + "BasicTests", getAdminCredentials().getSessionToken());
    }
    
    @Test
    @org.junit.jupiter.api.Order(300)
    void createAllOrder(){
        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
        this.createAllItems("createAll" + getClassName());
    }
    
    @Test
    @org.junit.jupiter.api.Order(400)
    void getOrdersBasicTests(){
        getItemBasicTests("get" + getClassName() + "BasicTests", getUserCredentials().getSessionToken());
    }
  
    @Test
    @org.junit.jupiter.api.Order(500)
    void getOrderById(){
        getItemById("get" + getClassName() + "ById");
    }
      
    @Test
    @org.junit.jupiter.api.Order(550)
    void getNoExistingOrderById(){
        getNoExistingItemById("getNoExisting" + getClassName() + "ById");
    }
    
    @Test
    @org.junit.jupiter.api.Order(600)
    void updateOrderBasicTests(){
        updateItemBasicTests("create" + getClassName() + "BasicTests", getAdminCredentials().getSessionToken());
    }
    
    @Test
    @org.junit.jupiter.api.Order(700)
    void updateOrder(){
        this.updateItem("update" + getClassName());       
    }
    
    @Test
    @org.junit.jupiter.api.Order(800)
    void deleteOrderBasicTests(){
        deleteItemBasicTests("delete" + getClassName() + "BasicTests", getAdminCredentials().getSessionToken());
    }
     @Test
    @org.junit.jupiter.api.Order(850)
    void getToDeletOrder(){
        getItemToDelete("getToDele" + getClassName());
    }
           
    @Test
    @org.junit.jupiter.api.Order(900)
    void deleteOrder(){
        deleteItem("delete"+ getClassName());
    }
          
    @Test
    @org.junit.jupiter.api.Order(1000)
    void getDeletedOrder(){
        getDeletedItem("getDeleted" + getClassName());
    }

     
}