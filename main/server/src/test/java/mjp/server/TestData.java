package mjp.server;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mjp.server.ServerMJP.database.Order;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.queryData.table.TableGetInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import testUtils.Credentials;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author twiki
 */
public class TestData {
//    public Credentials userCredentials;
//    public Credentials adminCredentials;
    public Credentials  userCredentials = new Credentials("Twiki", "Tuki", null);
    public Credentials adminCredentials = new Credentials("Ping", "Pong", null);
   
    public TableRestaurant initialTable;
    public TableRestaurant updatedTable;
    public TableRestaurant mockTable1;
    public TableRestaurant mockTable2;
    public List<TableRestaurant> allTables;
    
    
    public SessionService initialSessionService;
    public SessionService mockSessionService1;    
    public SessionService mockSessionService2;  
    public SessionService mockSessionService3;
    public SessionService openSessionService;
    public List<SessionService> allSessionService;
    public SessionService updatedSessionService;
    public SessionService noExsistingSessionService;
    
    
    public Order initialOrder;
    public Order mockOrder1;    
    public Order mockOrder2;  
    public Order mockOrder3;
    public Order openOrder;
    public List<Order> allOrder;
    public Order updatedOrder;
    public Order noExsistingOrder;
        
    /**************************************************************************/
    /*** TableRestaurant                                                    ***/
    /**************************************************************************/
    public void refreshTables() {
        initialTable = allTables.get(0);
        updatedTable.setId(initialTable.getId());
        mockTable1 = allTables.get(1);
        mockTable2 = allTables.get(2);
    }
    public void initTablesData() {
        initialTable = new TableRestaurant(10, 4);
        updatedTable = new TableRestaurant(10, 2);
        mockTable1 = new TableRestaurant(5, 2);
        mockTable2 = new TableRestaurant(4, 6);
        allTables = new ArrayList<>(List.of(initialTable, mockTable1, mockTable2));
    }
    public void logins() {
        
    }
    
    /**************************************************************************/
    /*** SessionService                                                     ***/
    /**************************************************************************/
    public void refreshSessionService() {
        initialSessionService = allSessionService.get(0);
        updatedSessionService.setId(initialSessionService.getId());
        mockSessionService1 = allSessionService.get(1);
        mockSessionService2 = allSessionService.get(2);
        mockSessionService3 = allSessionService.get(3);
        openSessionService = allSessionService.get(4);
    }
    public void initSessionServicesData() {
//        initTablesData();
        dummyInitSessionServices();
        
        
        initialSessionService = new SessionService(
            initialTable.getId(),
            0,
            4,
            1,
            2,
            LocalDateTime.of(2020, Month.DECEMBER, 12, 20, 15, 3),
            LocalDateTime.of(2020, Month.DECEMBER, 12, 20, 15, 3),
            SessionService.SessionServiceStatus.PAID,
            5,
                ""
        );

        mockSessionService1 = new SessionService(
            mockTable1.getId(),
            1,
            4,
            1,
            2,
            LocalDateTime.of(2020, Month.DECEMBER, 13, 20, 15, 3),
            LocalDateTime.of(2020, Month.DECEMBER, 13, 20, 15, 3),
            SessionService.SessionServiceStatus.PAID,
            5,
            "Very Nice place"
        );    

        mockSessionService2 = new SessionService(
            mockTable2.getId(),
            1,
            4,
            1,
            2,
            LocalDateTime.of(2020, Month.DECEMBER, 14, 20, 15, 3),
            LocalDateTime.of(2020, Month.DECEMBER, 14, 20, 15, 3),
            SessionService.SessionServiceStatus.PAID,
            5,
            "Very Nicer place"
        );  

        mockSessionService3 = new SessionService(
            mockTable1.getId(),
            1,
            4,
            1,
            2,
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 15, 3),
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 15, 3),
            SessionService.SessionServiceStatus.PAID,
            5,
            "Very Nicer placer"
        );
        
        
        
        openSessionService = new SessionService(
            initialTable.getId(),
            0,
            4,
            1,
            2,
            LocalDateTime.of(2020, Month.DECEMBER, 12, 20, 15, 3),
            LocalDateTime.of(2020, Month.DECEMBER, 12, 20, 15, 3),
            SessionService.SessionServiceStatus.OPEN,
            5,
                ""
        );


        allSessionService = new ArrayList(List.of(initialSessionService, mockSessionService1, mockSessionService2, mockSessionService3, openSessionService));

        noExsistingSessionService = new SessionService(
            500000L, 
            mockTable1.getId(),
            3,
            4,
            1,
            2,
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 15, 3),
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 15, 3),
            SessionService.SessionServiceStatus.PAID,
            5,
            "Very Nicer placer"
        );

        updatedSessionService = new SessionService(
            initialTable.getId(),
            0,
            4,
            1,
            2,
            LocalDateTime.of(2020, Month.DECEMBER, 12, 20, 15, 3),
            LocalDateTime.of(2020, Month.DECEMBER, 12, 20, 15, 3),
            SessionService.SessionServiceStatus.PAID,
            5,
            "Nice Place"
        );
    }

    public void dummyInitSessionServices() {
        
    }
     
    /**************************************************************************/
    /*** SessionService                                                     ***/
    /**************************************************************************/
    public void refreshOrder() {
        initialOrder = allOrder.get(0);
        updatedOrder.setId(initialOrder.getId());
        mockOrder1 = allOrder.get(1);
        mockOrder2 = allOrder.get(2);
        mockOrder3 = allOrder.get(3);
        openOrder = allOrder.get(4);
    }
    public void initOrderData() {
//        initTablesData();
        dummyOrderServices();
        
        
        initialOrder = new Order(
            initialSessionService.getId(),
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 15, 3),
            Order.Status.OPEN
        );

        mockOrder1 = new Order(
            initialSessionService.getId(),
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 16, 3),
            Order.Status.OPEN
        );    

        mockOrder2 = new Order(
            initialSessionService.getId(),
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 17, 3),
            Order.Status.OPEN
        );  

        mockOrder3 = new Order(
            initialSessionService.getId(),
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 18, 3),
            Order.Status.OPEN
        );
        
        openOrder = new Order(
            initialSessionService.getId(),
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 19, 3),
            Order.Status.OPEN
        );

        allOrder = new ArrayList(List.of(initialOrder, mockOrder1, mockOrder2, mockOrder3, openOrder));

        noExsistingOrder = new Order(
            5000L,
            initialSessionService.getId(),
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 19, 3),
            Order.Status.SENDED
        );

        updatedOrder = new Order(
            initialSessionService.getId(),
            LocalDateTime.of(2020, Month.DECEMBER, 15, 20, 15, 3),
            Order.Status.OPEN
        );
    }
    
    public Order getOrderByIdFromAllOrders(Long id) {
        for (Order order : allOrder){
            if (Objects.equals(order.getId(), id))
                return order;
        }
        return null;
    }
    
    public void dummyOrderServices() {

    }
}
