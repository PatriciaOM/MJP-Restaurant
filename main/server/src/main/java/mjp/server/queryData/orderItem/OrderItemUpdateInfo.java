/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.orderItem;

import mjp.server.ServerMJP.database.OrderItem;
import mjp.server.queryData.defaults.UpdateInfo;

/**
 * Class for holding the information of an OrderItem update request
 * @author Joan Renau Valls
 */
public class OrderItemUpdateInfo extends UpdateInfo<OrderItem>  {    
    
    public OrderItemUpdateInfo(){};
   
    public OrderItemUpdateInfo(String sessionToken, OrderItem orderItem) {
        this.sessionToken = sessionToken;
        this.item = orderItem;
    }  
        
    public OrderItemUpdateInfo(OrderItemUpdateInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.item = orig.getItem();
    }
}
