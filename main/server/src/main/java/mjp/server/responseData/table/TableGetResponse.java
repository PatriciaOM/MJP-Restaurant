/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.table;

import java.util.List;
import mjp.server.ServerMJP.database.TableRestaurant;

/**
 *
 * @author twiki
 */
public class TableGetResponse {
    
    private List<TableRestaurant> tables;
    
    public TableGetResponse(List<TableRestaurant> tables) {
        this.tables = tables;
    }
    
    void setTables(List<TableRestaurant> val) {
        this.tables = val;
    }
    
    public List<TableRestaurant> getTables() {
        return this.tables;
    }
    
}
