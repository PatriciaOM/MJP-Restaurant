package mjp.server;

import java.util.ArrayList;
import java.util.List;
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
    
    
    public void initTablesData() {
        initialTable = new TableRestaurant(10, 4);
        updatedTable = new TableRestaurant(10, 2);
        mockTable1 = new TableRestaurant(5, 2);
        mockTable2 = new TableRestaurant(4, 6);
        allTables = new ArrayList<>(List.of(initialTable, mockTable1, mockTable2));
    }
    public void logins() {
        
    }

}
