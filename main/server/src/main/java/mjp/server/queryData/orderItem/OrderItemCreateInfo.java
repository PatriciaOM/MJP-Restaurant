/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.orderItem;

import mjp.server.ServerMJP.database.OrderItem;
import mjp.server.queryData.defaults.CreateInfo;


/**
 * Class for holding the information of an OrderItem create request
 * @author Joan Renau Valls
 */
public class OrderItemCreateInfo extends CreateInfo<OrderItem>{    
    
    public OrderItemCreateInfo() {
        super();
    }  
    
    public OrderItemCreateInfo(String sessionToken, OrderItem item) {
        super(sessionToken, item);
    }  
}
