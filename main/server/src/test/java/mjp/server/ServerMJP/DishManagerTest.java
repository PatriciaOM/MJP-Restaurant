/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.util.List;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.dish.DishCreateInfo;
import mjp.server.queryData.dish.DishGetInfo;
import mjp.server.queryData.table.TableCreateInfo;
import mjp.server.responseData.dish.DishCreateResponse;
import mjp.server.responseData.dish.DishGetResponse;
import mjp.server.responseData.dish.DishResponse;
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
import testUtils.TestDefaultClass;


@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DishManagerTest extends TestDefaultClass {

    
    private static String userSessionToken;
    private static String adminSessionToken;
    Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass
    
    @LocalServerPort
    private int port;
    public int getPort() { return this.port; }
    
    @Autowired
    private TestRestTemplate restTemplate;
    public TestRestTemplate getRestTemplate() { return this.restTemplate; }
    
    static Dish initialDish = new Dish(
        "Braves",
        4.20f,
        "Prova les nostres braves",
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
    
    List<Dish> allDishes = List.of(initialDish, mockDish1, mockDish2, mockDish3);
    
       
    @Test
    @Order(001)
    void setup(){
        printTestName("setup");
        userSessionToken = this.login("Twiki", "Tuki");
        adminSessionToken = this.login("Ping", "Pong");
        System.out.println("usersSessionToken=" + userSessionToken);
        System.out.println("adminSessionToken=" + adminSessionToken);
        User user = this.getUserBySessionToken(userSessionToken);
        User admin = this.getUserBySessionToken(adminSessionToken);
        System.out.println("User user to json: " + gson.toJson(user));
        System.out.println("Admin user to json: " + gson.toJson(admin));
        assertThat(user.getRole()).isEqualTo(UserRole.USER);
        assertThat(admin.getRole()).isEqualTo(UserRole.ADMIN);
    }
    
    @Test
    @Order(200)
    void createDishBasicTests(){
        DishCreateInfo createInfo =  new DishCreateInfo();
        this.basicRequestTests(
            "createDish",
            "/dish/create",
            createInfo,
            initialDish,
            adminSessionToken
        );
    }
    
    @Test
    @Order(300)
    void createAllDishes(){
        printTestName("createAllDishes");
        String url = makeUrl("/dish/create");//
//        Dish dish = initialDish;
        DishCreateInfo info;
        
        for (Dish dish : allDishes){
            info = new DishCreateInfo(adminSessionToken, dish);
            ResponseEntity<String> response = makePostRequest(url, info);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            DishCreateResponse createReponse = gson.fromJson(response.getBody(), DishCreateResponse.class);
            assertThat(createReponse.getMessageStatus()).isEqualTo("Success");
            assertThat(createReponse.getDishes().size()).isEqualTo(1);
            Dish dishResponse = createReponse.getDishes().get(0);
            assertNotNull(dishResponse.getId());
            dish.setId(dishResponse.getId());
            assertThat(gson.toJson(dishResponse)).isEqualTo(gson.toJson(dish));
        }
        assertNotNull(allDishes.get(0).getId());
        assertNotNull(initialDish.getId());
    }
    
    @Test
    @Order(400)
    void getDishBasicTests(){
        DishGetInfo getInfo =  new DishGetInfo();
        this.basicRequestTests(
            "getDishBasicTests",
            "/dish/get",
            getInfo,
            null,
            userSessionToken
        );
    }
  
    @Test
    @Order(500)
    void getDishById(){
        printTestName("getDish");
        String url = makeUrl("/dish/get");
        DishGetInfo info = new DishGetInfo(userSessionToken, initialDish.getId());
        assertNotNull(initialDish.getId());
        assertNotNull(info.getId());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DishGetResponse responseMessage = gson.fromJson(response.getBody(), DishGetResponse.class);
        List<Dish> dishes = responseMessage.getDishes();
        assertThat(dishes.size()).isEqualTo(1);
        assertThat(gson.toJson(dishes.get(0))).isEqualTo(gson.toJson(initialDish));
    }
}