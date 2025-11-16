package com.mjprestaurant.model.table;

import java.util.List;

/**
 * Classe que retorna el servidor amb la informació de la taula
 * @author Patricia Oliva
 */
public class TableGetResponse {
    
    private List<TableRestaurant> tables;
    
    /**
     * Constructor per defecte
     */
    public TableGetResponse() {
    }

    /**
     * Constructor principal
     * @param tables llista de taules
     */
    public TableGetResponse(List<TableRestaurant> tables) {
        this.tables = tables;
    }
    
    /**
     * Inicialitza la llista de taules
     * @param val llista de taules
     */
    void setTables(List<TableRestaurant> val) {
        this.tables = val;
    }
    
    /**
     * Retorna la llista de taules
     * @return llista de taules
     */
    public List<TableRestaurant> getTables() {
        return this.tables;
    }
    

}
