/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.util.Objects;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.queryData.TableStatusInfo;
import mjp.server.responseData.TableStatusResponse;
import mjp.server.responseData.TableStatusResponseElement;
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
public class TableManagementStatusTest extends TestDefault {
    private static String userSessionToken;
    private static String adminSessionToken;
    private static final TableRestaurant initialTable = new TableRestaurant(10, 4);
    private static final TableRestaurant mockTable1 = new TableRestaurant(5, 2);
    private static final TableRestaurant mockTable2 = new TableRestaurant(4, 6);
//    private static final List<TableRestaurant> allTables = List.of(initialTable, mockTable1, mockTable2);
    private static final TableRestaurant updatedTable = new TableRestaurant(10, 2);
    private static final long noExistingId = 50000;
    Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass
    
    
    @LocalServerPort
    private int port;
    public int getPort() { return this.port; }
    
    @Autowired
    private TestRestTemplate restTemplate;
    public TestRestTemplate getRestTemplate() { return this.restTemplate; }
    
    TableRestaurant getTableByIdFromAllTables(Long id) {
        for(TableRestaurant table : defaultData.allTables){
            if (Objects.equals(table.getId(), id))
                return table;
        }
        return null;
    }
    
    @Test
    @Order(001)
    void setup(){   
        setDefaultDataLogins();  
        
        defaultData.initTablesData();
        createDefaultDataTables();
        defaultData.initSessionServicesData();        
        creteDefaultDataSessionService();
        
        userSessionToken = defaultData.userCredentials.getSessionToken();
        adminSessionToken = defaultData.adminCredentials.getSessionToken();
        assertNotNull(userSessionToken);
        assertNotNull(adminSessionToken);
        assertNotNull(defaultData.allTables.get(0).getId());
    }
    
//    @Test
//    @Order(002)
//    void createTableTwice(){
//        printTestName("createTable");
//        String url = makeUrl("/table/create");
//        for (TableRestaurant table : allTables){
//            TableCreateInfo info = new TableCreateInfo(adminSessionToken, table);
//
//            ResponseEntity<String> response = makePostRequest(url, info);
//            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//            TableCreateResponse responseObject = gson.fromJson(response.getBody(), TableCreateResponse.class);
//            assertThat(responseObject.getMessage()).isEqualTo("success");
//            TableRestaurant tableResponse = responseObject.getTable();
//
//            table.setId(tableResponse.getId());
//            assertThat(gson.toJson(tableResponse)).isEqualTo(gson.toJson(table));
//
////            response = makePostRequest(url, info);
////            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
//        }
//        updatedTable.setId(initialTable.getId());
//    }
    
    
    @Test
    @Order(100)
    void TableStatus(){
        printTestName("TableStatus");
        assertNotNull(userSessionToken);
        TableStatusInfo request = new TableStatusInfo(userSessionToken, null);
        ResponseEntity<String> response = makePostRequest("/table/status", request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("Received tables status: " + response.getBody());
        TableStatusResponse responseObject = gson.fromJson(response.getBody(), TableStatusResponse.class);
        for (TableStatusResponseElement table: responseObject.getTables()){
            TableRestaurant origTable = getTableByIdFromAllTables(table.getId());
            assertNotNull(origTable);
            assertThat(table.getMaxClients()).isEqualTo(origTable.getMaxGuests());
            if (Objects.equals(table.getId(), defaultData.initialSessionService.getId()))
                assertThat(table.getClientsAmount()).isEqualTo(defaultData.initialSessionService.getClients());
        }
    }
    
}
