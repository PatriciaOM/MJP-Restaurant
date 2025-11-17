/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.order;

import mjp.server.ServerMJP.database.Order;
import mjp.server.queryData.defaults.UpdateInfo;

/**
 *
 * @author twiki
 */
public class OrderUpdateInfo extends UpdateInfo<Order>  {    
    
    public OrderUpdateInfo(){};
   
    public OrderUpdateInfo(String sessionToken, Order order) {
        this.sessionToken = sessionToken;
        this.item = order;
    }  
        
    public OrderUpdateInfo(OrderUpdateInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.item = orig.getItem();
    }
}
