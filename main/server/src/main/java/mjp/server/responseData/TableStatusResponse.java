package mjp.server.responseData;

import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author twiki
 */
public class TableStatusResponse {
    private ArrayList<TableStatusResponseElement> tables = new ArrayList();
    
    ArrayList<TableStatusResponseElement> getTables() { return this.tables; }
    
    public void addTable(TableStatusResponseElement table) {
        this.tables.add(table);
    }
    
}
