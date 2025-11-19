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
import mjp.server.TestData;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.table.TableCreateInfo;
import mjp.server.queryData.table.TableDeleteInfo;
import mjp.server.queryData.table.TableGetInfo;
import mjp.server.queryData.table.TableUpdateInfo;
import mjp.server.responseData.table.TableCreateResponse;
import mjp.server.responseData.table.TableGetResponse;
import mjp.server.responseData.table.TableUpdateResponse;
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
import testUtils.TestDefault;

/**
 *
 * @author  Joan Renau Valls
 */


@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TableManagementTest extends TestDefault {
    private static String userSessionToken;
    private static String adminSessionToken;
//    private static final TableRestaurant initialTable = TestDefault.defaultData.initialTable;
//    private static final TableRestaurant updatedTable = TestDefault.defaultData.updatedTable;
//    private static final TableRestaurant mockTable1 = TestDefault.defaultData.mockTable1;
//    private static final TableRestaurant mockTable2 = TestDefault.defaultData.mockTable2;
    private static final long noExistingId = 50000;
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
        defaultData.initTablesData();
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
        TableRestaurant table = defaultData.initialTable;
        TableCreateInfo info = new TableCreateInfo(null, table);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    
    @Test
    @Order(200)
    void createTableWhithNoTable(){
        printTestName("createTableWhithNoTable");
        String url = makeUrl("/table/create");//
        TableRestaurant table = defaultData.initialTable;
        TableCreateInfo info = new TableCreateInfo(adminSessionToken, null);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
     
    @Test
    @Order(300)
    void createTableWithInvalidCredentials(){
        printTestName("createTableWithInvalidCredentials");
        String url = makeUrl("/table/create");
        TableRestaurant table = defaultData.initialTable;
        TableCreateInfo info = new TableCreateInfo(userSessionToken, table);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    @Order(400)
    void createTableTwice(){
        printTestName("createTable");
        String url = makeUrl("/table/create");
        TableRestaurant table = defaultData.initialTable;
        TableCreateInfo info = new TableCreateInfo(adminSessionToken, table);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TableCreateResponse responseObject = gson.fromJson(response.getBody(), TableCreateResponse.class);
        assertThat(responseObject.getMessage()).isEqualTo("success");
        TableRestaurant tableResponse = responseObject.getTable();
        assertThat(tableResponse.getNum()).isEqualTo(table.getNum());
        assertThat(tableResponse.getMaxGuests()).isEqualTo(table.getMaxGuests());
        
        defaultData.initialTable.setId(tableResponse.getId());
        defaultData.initialTable.setId(tableResponse.getId());
        defaultData.updatedTable.setId(tableResponse.getId());
        defaultData.updatedTable.setId(tableResponse.getId());
        assertThat(gson.toJson(tableResponse)).isEqualTo(gson.toJson(defaultData.initialTable));
        
        
        response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
    }
    
    @Test
    @Order(500)
    void createMoreTablesAndGetAll(){
        printTestName("createMoreTablesAndGetAll");
        String url = makeUrl("/table/create");
        TableCreateResponse responseObject;
        
        TableCreateInfo info = new TableCreateInfo(adminSessionToken, defaultData.mockTable1);
        ResponseEntity<String> response = makePostRequest(url, info);
        TableCreateResponse responseTable; 
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        responseObject = gson.fromJson(response.getBody(), TableCreateResponse.class);
        defaultData.mockTable1.setId(responseObject.getTable().getId());
        
        info = new TableCreateInfo(adminSessionToken, defaultData.mockTable2);
        response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        responseObject = gson.fromJson(response.getBody(), TableCreateResponse.class);
        defaultData.mockTable2.setId(responseObject.getTable().getId());
        
        url = makeUrl("/table/get");
        TableGetInfo getInfo = new TableGetInfo(userSessionToken);
        response = makePostRequest(url, getInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TableGetResponse getResponseTable = gson.fromJson(response.getBody(), TableGetResponse.class);
        List<TableRestaurant> tables = getResponseTable.getTables();
        assertThat(tables.size()).isEqualTo(3);
                
        ArrayList<Integer> CurrentTables = new ArrayList(List.of(defaultData.initialTable.getNum(), defaultData.mockTable1.getNum(), defaultData.mockTable2.getNum()));
        for (TableRestaurant table : tables){
            assertThat(CurrentTables.remove(Integer.valueOf(table.getNum()))).isEqualTo(true);
        }
        
    }
    
    @Test
    @Order(600)
    void getTableWithNoSessionToken() {
        printTestName("getTableWithNoSessionToken");
        String url = makeUrl("/table/get");
        assertNotNull(defaultData.initialTable.getId());
        TableGetInfo info = new TableGetInfo(null, defaultData.initialTable.getNum());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
       
    @Test
    @Order(700)
    void getTableWithInvalidSessionToken() {
        printTestName("getTableWithInvalidSessionToken");
        String url = makeUrl("/table/get");
        assertNotNull(defaultData.initialTable.getId());
        TableGetInfo info = new TableGetInfo("INVALID_SESSION_TOKEN", defaultData.initialTable.getNum());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    @Order(750)
    void getTableWithNoExistingId(){
        printTestName("getTableById");
        String url = makeUrl("/table/get");
        assertNotNull(defaultData.initialTable.getId());
        
        TableGetInfo info = new TableGetInfo(userSessionToken, noExistingId);
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
     
    @Test
    @Order(800)
    void getTableById(){
        printTestName("getTableById");
        String url = makeUrl("/table/get");
        assertNotNull(defaultData.initialTable.getId());
        TableGetInfo info = new TableGetInfo(userSessionToken, defaultData.initialTable.getId());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TableGetResponse responseObject = gson.fromJson(response.getBody(), TableGetResponse.class);
        List<TableRestaurant> tables = responseObject.getTables();
        assertThat(tables.size()).isEqualTo(1);
        assertThat(gson.toJson(tables.get(0))).isEqualTo(gson.toJson(defaultData.initialTable));
    }  
    
    @Test
    @Order(900)
    void getTableByNumber(){
        printTestName("getTableByNumber");
        String url = makeUrl("/table/get");
        assertNotNull(defaultData.initialTable.getId());
        TableGetInfo info = new TableGetInfo(userSessionToken, defaultData.initialTable.getNum());
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TableGetResponse responseObject = gson.fromJson(response.getBody(), TableGetResponse.class);
        List<TableRestaurant> tables = responseObject.getTables();
        assertThat(tables.size()).isEqualTo(1);
        assertThat(gson.toJson(tables.get(0))).isEqualTo(gson.toJson(defaultData.initialTable));
    }
         
    @Test
    @Order(1050)
    void updateWithNoSessionToken(){
        printTestName("updateWithNoSessionToken");
        String url = makeUrl("/table/update");
        TableUpdateInfo info = new TableUpdateInfo(null, defaultData.updatedTable);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
     
    @Test
    @Order(1100)
    void updateWithInvalidSessionToken(){
        printTestName("updateWithNoSessionToken");
        String url = makeUrl("/table/update");
        TableUpdateInfo info = new TableUpdateInfo("invalid_session_token", defaultData.updatedTable);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    } 
    
    @Test
    @Order(1200)
    void updateWithNoTable(){
        printTestName("updateWithNoSessionToken");
        String url = makeUrl("/table/update");
        TableUpdateInfo info = new TableUpdateInfo("invalid_session_token", null);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    
    @Test
    @Order(1300)
    void udateBasicTests(){
        this.basicRequestTests(
            "/table/update",
            "/table/update",
            new TableUpdateInfo(),
            defaultData.updatedTable,
            adminSessionToken
        );
    }
     
    @Test
    @Order(1400)
    void updateTable(){
        printTestName("updateTable");
        String url = makeUrl("/table/update");
        TableUpdateInfo info = new TableUpdateInfo(adminSessionToken, defaultData.updatedTable);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TableRestaurant table = gson.fromJson(response.getBody(), TableUpdateResponse.class).getTable();
        assertThat(gson.toJson(table)).isEqualTo(gson.toJson(defaultData.updatedTable));
        url = makeUrl("/table/get");
        TableGetInfo getInfo = new TableGetInfo(userSessionToken, defaultData.initialTable.getId());
        response = makePostRequest(url, getInfo);
        System.out.println("Checking the table has ben updted: " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TableRestaurant> tableList = gson.fromJson(response.getBody(), TableGetResponse.class).getTables();
        assertThat(tableList.size()).isEqualTo(1);
        table = tableList.get(0);
        assertThat(gson.toJson(table)).isEqualTo(gson.toJson(defaultData.updatedTable));        
    }
   
    @Test
    @Order(1500)
    void deleteTableBasicTests(){
        this.basicRequestTests(
            "deleteTableBasicTests",
            "/table/delete",
            new TableDeleteInfo(),
            null,
            adminSessionToken
        );
    }
     
    @Test
    @Order(1600)
    void deleteTableWithNoExisitingID(){
        String url = makeUrl("/table/delete");
        TableDeleteInfo info = new TableDeleteInfo(adminSessionToken, 5000L);
        
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    
         
    @Test
    @Order(1700)
    void deleteTable(){
        String url;
        TableGetInfo getInfo;
        TableDeleteInfo info;
        
        printTestName("deleteTable");
        
        url = makeUrl("/table/get");
        assertNotNull(defaultData.updatedTable.getId());
        getInfo = new TableGetInfo(userSessionToken, defaultData.initialTable.getId());
        ResponseEntity<String> getResponse = makePostRequest(url, getInfo);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        url = makeUrl("/table/delete");
        info = new TableDeleteInfo(adminSessionToken, defaultData.updatedTable.getId());
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        url = makeUrl("/table/get");
        assertNotNull(getInfo.getId());
        getResponse = makePostRequest(url, getInfo);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
