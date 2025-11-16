/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.Order;

import java.util.List;
import mjp.server.ServerMJP.database.Order;

/**
 *
 * @author twiki
 */
public class OrderUpdateResponse extends OrderResponse{
    
    public OrderUpdateResponse(){}
    
    public OrderUpdateResponse(String messageStatus, List<Order> items) {
        super(messageStatus, items);
    }
      
    public OrderUpdateResponse(OrderUpdateResponse orig) {
        super(orig);
    }
}
