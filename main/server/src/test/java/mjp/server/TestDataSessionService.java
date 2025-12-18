package mjp.server;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
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
public class TestDataSessionService {
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
}
