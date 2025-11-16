/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.Order;

import mjp.server.queryData.defaults.DeleteInfo;

/**
 *
 * @author twiki
 */
public class OrderDeleteInfo extends DeleteInfo<Long> {    
   public OrderDeleteInfo(){};
   
    public OrderDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }  
        
    public OrderDeleteInfo(OrderDeleteInfo orig) {
        this.sessionToken = orig.sessionToken;
    }
}
