/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.orderItem;

import mjp.server.queryData.defaults.DeleteInfo;

/**
 *
 * @author twiki
 */
public class OrderItemDeleteInfo extends DeleteInfo<Long> {    
   public OrderItemDeleteInfo(){};
   
    public OrderItemDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }  
        
    public OrderItemDeleteInfo(OrderItemDeleteInfo orig) {
        this.sessionToken = orig.sessionToken;
    }
}
