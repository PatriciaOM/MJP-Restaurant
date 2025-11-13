package com.mjprestaurant.model.table;

import java.util.List;

/**
 *
 * @author twiki
 */
public class TableGetResponse {
    
    private List<TableRestaurant> tables;
    
    public TableGetResponse() {
    }

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
