package com.mjprestaurant.model.order;

/**
 * Class for holding the information of an Order update request
 * @author Joan Renau Valls
 */
public class OrderUpdateInfo {  
    public String sessionToken;  
    public Order item;
    
    public OrderUpdateInfo(){};
   
    public OrderUpdateInfo(String sessionToken, Order order) {
        this.sessionToken = sessionToken;
        this.item = order;
    }  
        
    public OrderUpdateInfo(OrderUpdateInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.item = orig.getItem();
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Order getItem() {
        return item;
    }

    public void setItem(Order item) {
        this.item = item;
    }

}
