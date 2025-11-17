/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.orderItem;

import java.util.List;
import mjp.server.ServerMJP.database.OrderItem;

/**
 *
 * @author twiki
 */
public class OrderItemUpdateResponse extends OrderItemResponse{
    
    public OrderItemUpdateResponse(){}
    
    public OrderItemUpdateResponse(String messageStatus, List<OrderItem> items) {
        super(messageStatus, items);
    }
      
    public OrderItemUpdateResponse(OrderItemUpdateResponse orig) {
        super(orig);
    }
}
