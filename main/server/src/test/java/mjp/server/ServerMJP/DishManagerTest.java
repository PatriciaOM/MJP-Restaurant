/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.util.List;
import mjp.server.ServerMJP.database.DatabaseEntry;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.dish.DishCreateInfo;
import mjp.server.queryData.dish.DishDeleteInfo;
import mjp.server.queryData.dish.DishGetInfo;
import mjp.server.queryData.dish.DishUpdateInfo;
import mjp.server.queryData.table.TableCreateInfo;
import mjp.server.responseData.CrudResponse;
import mjp.server.responseData.dish.DishCreateResponse;
import mjp.server.responseData.dish.DishDeleteResponse;
import mjp.server.responseData.dish.DishGetResponse;
import mjp.server.responseData.dish.DishResponse;
import mjp.server.responseData.dish.DishUpdateResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
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
import testUtils.DataEntry;
import testUtils.TestDefaultCrud;
import testUtils.TestDefault;


@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class DishManagerTest extends TestDefaultCrud<
        Long,
        Dish,
        DishCreateResponse,
        DishGetResponse,
        DishUpdateResponse,
        DishDeleteResponse
    > {
    private static String resourceUri = "/dish";
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
    
    static Dish initialDish = new Dish(
        "Braves",
        4.20f,
        "Prova les nostres braves",
        true,
        Dish.DishCategory.APPETIZER
    );
    
    static Dish updatedDish = new Dish(
        "Braves extra picant",
        4.40f,
        "Nomes per valents",
        true,
        Dish.DishCategory.APPETIZER
            
    );
     
    static Dish mockDish1 = new Dish(
        "Espaguetis",
        7.20f,
        "Mama mia",
        false,
        Dish.DishCategory.MAIN
    );
    
    static Dish mockDish2 = new Dish(
        "Gelat",
        2.50f,
        "Bo i refrescant",
        true,
        Dish.DishCategory.DESSERT
    );

    static Dish mockDish3 = new Dish(
        "Aigua",
        1.50f,
        "Aigua que fa la vista clara",
        false,
        Dish.DishCategory.DRINK
    );
    
    
    static Dish noExsitingDish = new Dish(
        1000L,
        "Amanita muscria",
        0.00f,
        "Good luck eating this",
        false,
        Dish.DishCategory.APPETIZER
    );
    
    List<Dish> allDishes = List.of(initialDish, mockDish1, mockDish2, mockDish3);
    
    @Override
    protected Credentials getUserCredentials() {
        return this.user;
    }

    @Override
    protected Credentials getAdminCredentials() {
        return this.admin;
    }
 

    @Override
    protected Dish getInitialItem() {
        return initialDish;
    }

    @Override
    protected Dish getUpdatedItem() {
        return updatedDish;
    }

    @Override
    protected List<Dish> getAllTestItems() {
        return this.allDishes;
    }
    
    @Override
    protected Dish getNoExistingItem() {
        return noExsitingDish;
    }  
    
    @Override
    public AuthorizedQueryInfo<Dish> generateCreateRequest(String sessionToken, Dish entry) {
        return new DishCreateInfo(sessionToken, entry);
    }

    @Override
    public AuthorizedQueryInfo<Long> generateGetRequest(String sessionToken, Long entryId) {
        return new DishGetInfo(sessionToken, entryId);
    }
    
    @Override
    public AuthorizedQueryInfo<Dish> generateUpdateRequest(String sessionToken, Dish entry) {
        return new DishUpdateInfo(sessionToken, entry);
    }    
     
    @Override
    public AuthorizedQueryInfo<Long> generateDeleteRequest(String sessionToken, Long entryId) {
        return new DishDeleteInfo(sessionToken, entryId);
    }
    
    @Override
    public Class<DishCreateResponse> getCreateResponseClass() {
        return DishCreateResponse.class;
    }

    @Override
    public Class<DishGetResponse> getGetResponseClass() {
        return DishGetResponse.class;
    }

    @Override
    public Class<DishUpdateResponse> getUpdateResponseClass() {
        return DishUpdateResponse.class;
    }

    @Override
    public Class<DishDeleteResponse> getDeleteResponseClass() {
        return DishDeleteResponse.class;
    }   
    
    @Override
    public String getClassName() {
        return "Dish";
    }
    
    @Override
    public Credentials getCreateCredentials(){
        return this.getAdminCredentials();
    }

    @Override
    public Credentials getGetCredentials(){
        return this.getUserCredentials();
    }

    @Override
    public Credentials getUpdateCredentials(){
        return this.getAdminCredentials();
    }

    @Override
    public Credentials getDeleteCredentials(){
        return this.getAdminCredentials();
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
        createItemBasicTests("createSessionServiceBasicTests", getAdminCredentials().getSessionToken());
    }
    
    @Test
    @Order(300)
    void createAllSessionServices(){
        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
        this.createAllItems("createAllSessionServices");
    }
    
    @Test
    @Order(400)
    void getSessionServicesBasicTests(){
        getItemBasicTests("getSessionServicesBasicTests", getUserCredentials().getSessionToken());
    }
  
    @Test
    @Order(500)
    void getDishById(){
        getItemById("getDishById");
    }
      
    @Test
    @Order(550)
    void getNoExistingDishById(){
        getNoExistingItemById("getNoExistingDishById");
    }
    
    @Test
    @Order(600)
    void updateDishBasicTests(){
        updateItemBasicTests("createDishBasicTests", getAdminCredentials().getSessionToken());
    }
    
    @Test
    @Order(700)
    void updateDish(){
        this.updateItem("updateDish");       
    }
    
    @Test
    @Order(800)
    void deleteDishBasicTests(){
        deleteItemBasicTests("deleteDishBasicTests", getAdminCredentials().getSessionToken());
    }
     @Test
    @Order(850)
    void getToDeletDish(){
        getItemToDelete("getToDeletDish");
    }
           
    @Test
    @Order(900)
    void deleteDish(){
        deleteItem("deleteDish");
    }
          
    @Test
    @Order(1000)
    void getDeletedDish(){
        getDeletedItem("getDeletedDish");
    }
     
}