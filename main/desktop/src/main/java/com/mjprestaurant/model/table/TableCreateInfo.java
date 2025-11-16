package com.mjprestaurant.model.table;

/**
 * Classe per crear taules
 * @author Patricia Oliva
 */
public class TableCreateInfo {
    private String sessionToken;
    private TableRestaurant table;
    
    /**
     * Constructor per defecte
     */
    public TableCreateInfo(){};
   
    /**
     * Constructor principal
     * @param sessionToken token de sessió
     * @param table taula a afegir
     */
    public TableCreateInfo(String sessionToken, TableRestaurant table) {
        this.sessionToken = sessionToken;
        this.table = table;
    }  
        
    /**
     * Constructor que fa servir un objecte taula
     * @param orig taula original
     */
    public TableCreateInfo(TableCreateInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.table = orig.getTable();
    }
       
    /**
     * Inicialitza el token
     * @param val token de sessió
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
     * Retorna la taula a crear
     * @return taula
     */
    public TableRestaurant getTable() {
        return table;
    }

    /**
     * Inicialitza la taula a crear
     * @param table taula
     */
    public void setTable(TableRestaurant table) {
        this.table = table;
    }

}
