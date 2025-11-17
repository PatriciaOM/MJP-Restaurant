package com.mjprestaurant.model.table;

/**
 * Classe per eliminar una taula
 * @author Patricia Oliva
 */
public class TableDeleteInfo {
    
    private Long id;
    private String sessionToken;
    
    /**
     * Constructor per defecte
     */
    public TableDeleteInfo() {
        
    }
    
    /**
     * Constructor principal
     * @param sessionToken token de sessió
     * @param id id de la taula a eliminar
     */
    public TableDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }
    
    /**
     * Inicialitza l'id
     * @param val id de la taula a eliminar
     */
    public void setId(Long val) {
        this.id = val;
    }
    
    /**
     * Retorna l'id de la taula
     * @return id
     */
    public Long getId() {
        return this.id;
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

}
