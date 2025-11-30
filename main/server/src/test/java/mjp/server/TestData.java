package mjp.server;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.ServerMJP.database.Order;
import mjp.server.ServerMJP.database.OrderItem;
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
    
    public OrderItem initialOrderItem;
    public OrderItem mockOrderItem1;    
    public OrderItem mockOrderItem2;  
    public OrderItem mockOrderItem3;
    public OrderItem openOrderItem;
    public List<OrderItem> allOrderItem;
    public OrderItem updatedOrderItem;
    public OrderItem noExsistingOrderItem;
        
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
    /*** Order                                                     ***/
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
    
    
    
    /**************************************************************************/
    /*** OrderItem                                                          ***/
    /**************************************************************************/
    public void refreshOrderItem() {
        initialOrderItem = allOrderItem.get(0);
        updatedOrderItem.setId(initialOrderItem.getId());
        mockOrderItem1 = allOrderItem.get(1);
        mockOrderItem2 = allOrderItem.get(2);
        mockOrderItem3 = allOrderItem.get(3);
        openOrderItem = allOrderItem.get(4);
    }
    public void initOrderItemData() {
        System.out.println("Initializing OrderItemData");
        initialOrderItem = new OrderItem(
            initialOrder.getId(),
            5000L,
            2,
            2.5f,
            "Patates",
            "Braves",
            Dish.DishCategory.APPETIZER
        );

        mockOrderItem1 = new OrderItem(
            initialOrder.getId(),
            5001L,
            2,
            2.6f,
            "Patates",
            "Rellenes",
            Dish.DishCategory.APPETIZER
        );    

        mockOrderItem2 = new OrderItem(
            initialOrder.getId(),
            5002L,
            2,
            2.6f,
            "Patates",
            "Allioli",
            Dish.DishCategory.APPETIZER
        );  

        mockOrderItem3 = new OrderItem(
            initialOrder.getId(),
            5003L,
            2,
            2.6f,
            "Patates",
            "estofades",
            Dish.DishCategory.APPETIZER
        );
        
        allOrderItem = new ArrayList<OrderItem>(List.of(initialOrderItem, mockOrderItem1, mockOrderItem2, mockOrderItem3));

        noExsistingOrderItem = new OrderItem(
            5000L,
            5000L,
            5004L,
            2,
            2.6f,
            "Patates",
            "estofades",
            Dish.DishCategory.APPETIZER
        );

        updatedOrderItem = new OrderItem(
            initialOrderItem.getId(),
            5000L,
            2,
            2.6f,
            "Patates",
            "estofadetes",
            Dish.DishCategory.APPETIZER
        );
    }
    
    public OrderItem getOrderItemByIdFromAllOrders(Long id) {
        for (OrderItem orderItem : allOrderItem){
            if (Objects.equals(orderItem.getId(), id))
                return orderItem;
        }
        return null;
    }
    
    
}
