/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.orderItem;

import mjp.server.ServerMJP.database.OrderItem;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.InfoData;


/**
 * Class for holding the information of an OrderItem get request
 * @author Joan Renau Valls
 */
public class OrderItemGetInfo extends InfoData implements AuthorizedQueryInfo<Long> {  
    
    /**
     * Represents the different search methods for this endpoint.
     */
    public enum SearchType{
        ALL,
        BY_ID,
        BY_ORDER_ID,
    }
    
    private String sessionToken;
    private OrderItem orderItem;
    private SearchType searchType;
    private Long id;
    private Long orderId;
     

   public OrderItemGetInfo(){}
   
     /**
     * Constructor for getting a OrderItem by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     */
    public OrderItemGetInfo(String sessionToken) {
        this.sessionToken = sessionToken;
        this.searchType = SearchType.ALL;
    }
   
   
     /**
     * Constructor for getting a OrderItem by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     * @param id 
     */
    public OrderItemGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
       
    public OrderItemGetInfo(String sessionToken, OrderItem orderItem, SearchType searchType, Long id) {
        this.sessionToken = sessionToken;
        this.orderItem = orderItem;
        this.searchType = searchType;
        this.id = id;
    }  
    
    
    
    /**
     * This constructor allows to specify the type of search and accepts value of  type Long as search criteria (searchVal)
     * @param sessionToken
     * @param searchVal the value to use on the search
     * @param searchType the type of search
     */
    public OrderItemGetInfo(String sessionToken, Long searchVal, SearchType searchType) {
        this.sessionToken = sessionToken;
        this.searchType = searchType;
        switch (searchType){
            case BY_ID:
                this.id = searchVal;
                break;
            case BY_ORDER_ID:
                this.orderId = searchVal;
                break;
            default: 
                throw new IllegalArgumentException(String.format("Search type %s not suprted for Long type ", searchType));
        }
    }
        
    /**
     * Copy constructor
     * @param orig 
     */
    public OrderItemGetInfo(OrderItemGetInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.orderItem = orig.getOrderItems();
        this.searchType = orig.searchType;
        this.id = orig.id;
    }
           
    @Override
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    @Override
    public String getSessionToken() {
        return this.sessionToken;
    }

    
    public OrderItem getOrderItems() {
        return orderItem;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    
    @Override
    public void setMessageData(Long requestItem) {
        setId(requestItem);
    }

    @Override
    public Long getMessageData() {
        return this.getId();
    }
    
    public SearchType getSearchType() {
        return searchType;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
          
}
