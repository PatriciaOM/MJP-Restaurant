package com.mjprestaurant.model.order;

/**
 * Class for holding the information of an Order get request
 * @author Joan Renau Valls
 */
public class OrderGetInfo {    
    /**
     * Represents the different search methods for this endpoint.
     */
    public enum SearchType{
        ALL,
        BY_ID,
        BY_SESSION_SERVICE_ID
    }
      
    /**
     * A valid session token with the required permissions.
     */
    private String sessionToken;
    private Order order;
    private SearchType searchType;
    private Long id;
    private Long sessionServiceId;
 

    public OrderGetInfo(){}
   
     /**
     * Constructor for getting an Order by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     */
    public OrderGetInfo(String sessionToken) {
        this.sessionToken = sessionToken;
        this.searchType = SearchType.ALL;
    }
   
     /**
     * Constructor for getting an Order by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     * @param id 
     */
    public OrderGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
    
    public OrderGetInfo(String sessionToken, Long searchVal, SearchType searchType) {
        this.sessionToken = sessionToken;
        this.searchType = searchType;
        switch (searchType){
            case BY_ID:
                this.id = searchVal;
                break;
            case BY_SESSION_SERVICE_ID:
                this.sessionServiceId = searchVal;
                break;
            default: 
                throw new IllegalArgumentException(String.format("Search type %s not suprted for Long type ", searchType));
        }
    }
       
    public OrderGetInfo(String sessionToken, Order order, SearchType searchType, Long id) {
        this.sessionToken = sessionToken;
        this.order = order;
        this.searchType = searchType;
        this.id = id;
    }  
       
    /**
     * Copy constructor
     * @param orig original Order
     */
    public OrderGetInfo(OrderGetInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.order = orig.getOrder();
        this.searchType = orig.searchType;
        this.id = orig.id;
    }
           
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    public Order getOrder() {
        return order;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setSessionServiceId(Long sessionServiceId) {
        this.sessionServiceId = sessionServiceId;
    }

    public Long getSessionServiceId() {
        return sessionServiceId;
    }
    
    public void setMessageData(Long requestItem) {
        setId(requestItem);
    }
    
    public Long getMessageData() {
        return this.getId();
    }
}
