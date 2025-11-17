package com.mjprestaurant.model.table;

/**
 * Classe per actualitzar taules
 * @author Patricia Oliva
 */
public class TableUpdateInfo {
    
    private String sessionToken;
    private TableRestaurant table;
    
    /**
     * Constructor per defecte
     */
    public TableUpdateInfo(){};
    
    /**
     * Constructor principal
     * @param sessionToken token de sessió
     * @param table taula a actualitzar
     */
    public TableUpdateInfo(String sessionToken, TableRestaurant table) {
        this.sessionToken = sessionToken;
        this.table = table;
    }
    
    /**
     * Inicialitza el token de sessió
     * @param val token
     */
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    /**
     * Retorna el token de sessió
     * @return token
     */
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    /**
     * Inicialitza la taula a actualitzar
     * @param val taula
     */
    public void setTable(TableRestaurant val) {
        this.table = val;
    }
    
    /**
     * Retorna la taula
     * @return taula a actualitzar
     */
    public TableRestaurant getTable() {
        return this.table;
    }

    public void setMessageData(TableRestaurant requestItem) {
        this.setTable(requestItem);
    }

    public TableRestaurant getMessageData() {
        return this.getTable();
    }

}
