package com.mjprestaurant.model.table;

/**
 * Classe de consulta de taules
 * @author Patricia Oliva
 */
public class TableGetInfo {
    
    /**
     * Tipus de cerca
     */
    public enum SearchType{
        ALL,
        BY_ID,
        BY_NUMBER
    }
    
    private long id = -1;
    public int number = -1; 
    private String sessionToken = null;
    private SearchType searchType = SearchType.ALL;
    
    /**
     * Constructor per defecte
     */
    public TableGetInfo(){}
    
    /**
     * Constructor que retorna totes les taules. Necessita el token de sessió
     * @param sessionToken sessió
     */
    public TableGetInfo(String sessionToken){
        this.searchType = SearchType.ALL;
        this.sessionToken = sessionToken;
    }
     
    /**
     * Constructor que retorna una taula per id. Necessita el token de sessió i l'id de la taula a consultar
     * @param sessionToken sessió
     * @param id id de la taula
     */
    public TableGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
    
    /**
     * Constructor que retorna una taula pel seu numero
     * @param sessionToken sessió
     * @param number número de taula 
     */
    public TableGetInfo(String sessionToken, int number) {
        this.sessionToken = sessionToken;
        this.number = number;
        this.searchType = SearchType.BY_NUMBER;
    }
    
    /**
     * Inicialitza la sessió
     * @param val token de sessió
     */
    void setSessionToken(String val) {
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
     * Inicialitza el mètode de cerca
     * @param searchType mètode de cerca
     */
    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    /**
     * Retorna el mètode de cerca
     * @return mètode de cerca
     */
    public SearchType getSearchType() {
        return searchType;
    }
     
    /**
     * Retorna l'id de la taula
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Inicialitza l'id de la taula
     * @param id id de la taula
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Inicialitza el número de la taula
     * @param val número de taula
     */
    void setNumber(int val) {
        this.number = val;
    }
    
    /**
     * Retorna el número de taula
     * @return numero de taula
     */
    public int getNumber() {
        return this.number;
    }
    
}
