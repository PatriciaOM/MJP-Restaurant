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
public class OrderItemGetResponse extends OrderItemResponse{
    
    public OrderItemGetResponse(){}
    
    public OrderItemGetResponse(String messageStatus, List<OrderItem> items) {
        super(messageStatus, items);
    }
      
    public OrderItemGetResponse(OrderItemGetResponse orig) {
        super(orig);
    }
}
