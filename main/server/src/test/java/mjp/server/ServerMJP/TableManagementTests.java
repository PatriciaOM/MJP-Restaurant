/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.table.TableCreateInfo;
import mjp.server.queryData.table.TableDeleteInfo;
import mjp.server.queryData.table.TableGetInfo;
import mjp.server.queryData.table.TableUpdateInfo;
import mjp.server.responseData.table.TableCreateResponse;
import mjp.server.responseData.table.TableGetResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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

/**
 *
 * @author twiki
 */


@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TableManagementTests extends TestDefaultClass {
    private static String userSessionToken;
    private static String adminSessionToken;
    private static TableRestaurant initialTable = new TableRestaurant(10, 4);
    private static TableRestaurant updatedTable = new TableRestaurant(10, 2);
    private static TableRestaurant mockTable1 = new TableRestaurant(5, 2);
    private static TableRestaurant mockTable2 = new TableRestaurant(4, 6);
    Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass
    
    
    @LocalServerPort
    private int port;
    public int getPort() { return this.port; }
    
    @Autowired
    private TestRestTemplate restTemplate;
    public TestRestTemplate getRestTemplate() { return this.restTemplate; }
    
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
    @Order(100)
    void createTableWhithNoSessionToken(){
        printTestName("createTableWhithNoSessionToken");
        String url = makeUrl("/table/create");//
        TableRestaurant table = initialTable;
        TableCreateInfo info = new TableCreateInfo(null, table);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    
    @Test
    @Order(100)
    void createTableWhithNoTable(){
        printTestName("createTableWhithNoTable");
        String url = makeUrl("/table/create");//
        TableRestaurant table = initialTable;
        TableCreateInfo info = new TableCreateInfo(adminSessionToken, null);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
     
    @Test
    @Order(300)
    void createTableWithInvalidCredentials(){
        printTestName("createTableWithInvalidCredentials");
        String url = makeUrl("/table/create");
        TableRestaurant table = initialTable;
        TableCreateInfo info = new TableCreateInfo(userSessionToken, table);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    @Order(400)
    void createTableTwice(){
        printTestName("createTable");
        String url = makeUrl("/table/create");
        TableRestaurant table = initialTable;
        TableCreateInfo info = new TableCreateInfo(adminSessionToken, table);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TableCreateResponse responseObject = gson.fromJson(response.getBody(), TableCreateResponse.class);
        assertThat(responseObject.getMessage()).isEqualTo("success");
        TableRestaurant tableResponse = responseObject.getTable();
        assertThat(tableResponse.getNum()).isEqualTo(table.getNum());
        assertThat(tableResponse.getMaxGuests()).isEqualTo(table.getMaxGuests());
        
        initialTable.setId(tableResponse.getId());
        assertThat(gson.toJson(tableResponse)).isEqualTo(gson.toJson(initialTable));
        
        
        response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
    }
    
    
    @Test
    @Order(500)
    void createMoreTablesAndGetAll(){
        printTestName("createMoreTablesAndGetAll");
        String url = makeUrl("/table/create");
        
        TableCreateInfo info = new TableCreateInfo(adminSessionToken, mockTable1);
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        info = new TableCreateInfo(adminSessionToken, mockTable2);
        response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        url = makeUrl("/table/get");
        TableGetInfo getInfo = new TableGetInfo(userSessionToken);
        response = makePostRequest(url, getInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TableGetResponse responseObject = gson.fromJson(response.getBody(), TableGetResponse.class);
        List<TableRestaurant> tables = responseObject.getTables();
        assertThat(tables.size()).isEqualTo(3);
                
        ArrayList<Integer> CurrentTables = new ArrayList(List.of(this.initialTable.getNum(), this.mockTable1.getNum(), this.mockTable2.getNum()));
        for (TableRestaurant table : tables){
            assertThat(CurrentTables.remove(Integer.valueOf(table.getNum()))).isEqualTo(true);
        }
        
    }
    
    
    @Test
    @Order(600)
    void getTableWithNoSessionToken() {
        printTestName("getTableWithNoSessionToken");
        String url = makeUrl("/table/get");
        assertNotNull(initialTable.getId());
        TableGetInfo info = new TableGetInfo(null, initialTable.getNum());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
       
    @Test
    @Order(700)
    void getTableWithInvalidSessionToken() {
        printTestName("getTableWithInvalidSessionToken");
        String url = makeUrl("/table/get");
        assertNotNull(initialTable.getId());
        TableGetInfo info = new TableGetInfo("INVALID_SESSION_TOKEN", initialTable.getNum());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
     
    @Test
    @Order(800)
    void getTableById(){
        printTestName("getTableById");
        String url = makeUrl("/table/get");
        assertNotNull(initialTable.getId());
        TableGetInfo info = new TableGetInfo(userSessionToken, initialTable.getId());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TableGetResponse responseObject = gson.fromJson(response.getBody(), TableGetResponse.class);
        List<TableRestaurant> tables = responseObject.getTables();
        assertThat(tables.size()).isEqualTo(1);
        assertThat(gson.toJson(tables.get(0))).isEqualTo(gson.toJson(initialTable));
    }  
    
    @Test
    @Order(900)
    void getTableByNumber(){
        printTestName("getTableByNumber");
        String url = makeUrl("/table/get");
        assertNotNull(initialTable.getId());
        TableGetInfo info = new TableGetInfo(userSessionToken, initialTable.getNum());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TableGetResponse responseObject = gson.fromJson(response.getBody(), TableGetResponse.class);
        List<TableRestaurant> tables = responseObject.getTables();
        assertThat(tables.size()).isEqualTo(1);
        assertThat(gson.toJson(tables.get(0))).isEqualTo(gson.toJson(initialTable));
    }
     
    @Test
    @Order(1000)
    void deleteTable(){
        String url = makeUrl("/table/delete");
        TableDeleteInfo info = new TableDeleteInfo(userSessionToken, 5000L);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.I_AM_A_TEAPOT);
    }
     
    @Test
    @Order(1500)
    void updateTable(){
        String url = makeUrl("/table/update");
        TableUpdateInfo info = new TableUpdateInfo(userSessionToken, updatedTable);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.I_AM_A_TEAPOT);
    }
    
    
 
}
