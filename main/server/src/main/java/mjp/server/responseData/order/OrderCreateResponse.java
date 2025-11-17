/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.order;

import java.util.List;
import mjp.server.ServerMJP.database.Order;

/**
 *
 * @author twiki
 */
public class OrderCreateResponse extends OrderResponse {
    
    public OrderCreateResponse(){}
    
    public OrderCreateResponse(String messageStatus, List<Order> items) {
        super(messageStatus, items);
    }
      
    public OrderCreateResponse(OrderCreateResponse orig) {
        super(orig);
    }
}
