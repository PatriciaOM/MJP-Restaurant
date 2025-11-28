package com.mjprestaurant.model.order;

/**
 * Classe que conté la informació de la comanda
 * @author Patricia Oliva
 */
public class OrderGetInfo {    
    /**
     * Representa els diferents mètodes de cerca del servidor
     */
    public enum SearchType{
        ALL,
        BY_ID,
        BY_SESSION_SERVICE
    }
    
    /**
     * Token de sessió
     */
    private String sessionToken;
    private Order order;
    private SearchType searchType;
    private Long id;
     
    /**
     * Constructor per defecte
     */
    public OrderGetInfo(){}
   
    /**
     * Constructor que rep el token de sessió
     * @param sessionToken token de sessió
     */
    public OrderGetInfo(String sessionToken) {
        this.sessionToken = sessionToken;
        this.searchType = SearchType.ALL;
    }
   
    /**
     * Constructor per cercar una comanda per id
     * @param sessionToken
     * @param id 
     */
    public OrderGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
       
    /**
     * Constructor que rep tota la informació
     * @param sessionToken token de la sessió
     * @param order comanda
     * @param searchType tipus de cerca
     * @param id id de la comanda
     */
    public OrderGetInfo(String sessionToken, Order order, SearchType searchType, Long id) {
        this.sessionToken = sessionToken;
        this.order = order;
        this.searchType = searchType;
        this.id = id;
    }  
        
    /**
     * Constructor que rep l'objecte 
     * @param orig objecte
     */
    public OrderGetInfo(OrderGetInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.order = orig.getOrder();
        this.searchType = orig.searchType;
        this.id = orig.id;
    }
           
    /**
     * Inicialitza el token de sessió
     * @param val token de sessió
     */
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    /**
     * Retorna el token de sessió
     * @return token de sessió
     */
    public String getSessionToken() {
        return this.sessionToken;
    }

    /**
     * Retorna la comanda
     * @return comanda
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Inicialitza l'id de la comanda
     * @param id id de la comanda
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Inicialitza el missatge de l'acció
     * @param requestItem missatge d'èxit
     */
    public void setMessageData(Long requestItem) {
        setId(requestItem);
    }

    /**
     * Retorna el tipus de cerca
     * @return tipus de cerca
     */
    public SearchType getSearchType() {
        return searchType;
    }

    /**
     * Retorna l'id de la comanda
     * @return id de comanda
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Retorna el missatge de l'acció feta
     * @return missatge d'èxit
     */
    public Long getMessageData() {
        return this.getId();
    }
}
