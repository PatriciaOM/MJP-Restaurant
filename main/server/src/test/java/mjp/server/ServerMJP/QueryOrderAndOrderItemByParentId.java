/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import mjp.server.ServerMJP.database.Order;
import mjp.server.queryData.order.OrderGetInfo;
import mjp.server.responseData.order.OrderGetResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import mjp.server.uitls.serializers.LocalDateTimeAdapter;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import testUtils.TestDefault;
import static testUtils.TestDefault.defaultData;
import testUtils.TestDefaultCrud;

/**
 *
 * @author Joan Renau Valls
 */

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class QueryOrderAndOrderItemByParentId extends TestDefault{
    private Gson gson = (new GsonBuilder())
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .create();
    
    
    @Test
    @org.junit.jupiter.api.Order(001)
    void setup(){
//        basicSetup("Order tests setup");
        
        setDefaultDataLogins();  
        
        defaultData.initTablesData();
        createDefaultDataTables();
        defaultData.initSessionServicesData();        
        createDefaultDataSessionService();
        defaultData.initOrderData();        
        createDefaultDataOrder(); 
        assertNotNull(defaultData.initialOrder.getId());
        
//        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
//        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
//        userSessionToken = this.getUserCredentials().getSessionToken(); // TODO delete
//        adminSessionToken = this.getAdminCredentials().getSessionToken(); // TODO delete
        
        
//        basicSetup("setup");
//        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
//        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
//        userSessionToken = this.getUserCredentials().getSessionToken(); // TODO delete
//        adminSessionToken = this.getAdminCredentials().getSessionToken(); // TODO delete
    }
      
    @Test
    @org.junit.jupiter.api.Order(1100)
    void getOrdersBySessionServiceId(){
        String url = "/order/get";
        OrderGetInfo info = new OrderGetInfo(defaultData.adminCredentials.getSessionToken(), defaultData.initialSessionService.getId(), OrderGetInfo.SearchType.BY_SESSION_SERVICE_ID);
        ResponseEntity<String> response = makePostRequest(url, info);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("::::::::::::::::: RESPONSE TO GET ORDERS BY SESSION ID");
        OrderGetResponse responseObject = gson.fromJson(response.getBody(), OrderGetResponse.class);
        System.out.println("Returnded: " + responseObject.getItems().size() + " items");
        for (Order order : responseObject.getItems()) {
            System.out.println("order: " + gson.toJson(order));
            String respOrder = gson.toJson(order);
            String orderFormOriginalList = gson.toJson(defaultData.getOrderByIdFromAllOrders(order.getId()));
            assertThat(respOrder).isEqualTo(orderFormOriginalList);
        }
    }

    @LocalServerPort
    private int port;
    @Override
    public int getPort() { return this.port; }
    
    @Autowired
    private TestRestTemplate restTemplate;
    @Override
    public TestRestTemplate getRestTemplate() { return this.restTemplate; }
    
}
