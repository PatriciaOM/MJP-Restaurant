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
    }
    
    private String sessionToken;
    private OrderItem orderItem;
    private SearchType searchType;
    private Long id;
     

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

    @Override
    public void setMessageData(Long requestItem) {
        setId(requestItem);
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public Long getId() {
        return id;
    }
    
    @Override
    public Long getMessageData() {
        return this.getId();
    }
}
