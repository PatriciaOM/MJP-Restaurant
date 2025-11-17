/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.order;

import mjp.server.ServerMJP.database.Order;
import mjp.server.queryData.defaults.CreateInfo;

/**
 *
 * @author twiki
 */
public class OrderCreateInfo extends CreateInfo<Order>{    
    
    public OrderCreateInfo() {
        super();
    }  
    
    public OrderCreateInfo(String sessionToken, Order item) {
        super(sessionToken, item);
    }  
}
