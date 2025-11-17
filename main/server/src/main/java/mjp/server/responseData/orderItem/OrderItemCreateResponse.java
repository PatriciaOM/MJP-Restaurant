/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.orderItem;

import java.util.List;
import mjp.server.ServerMJP.database.OrderItem;

/**
 *
 * @author Joan Renau Valls
 */
public class OrderItemCreateResponse extends OrderItemResponse {
    
    public OrderItemCreateResponse(){}
    
    public OrderItemCreateResponse(String messageStatus, List<OrderItem> items) {
        super(messageStatus, items);
    }
      
    public OrderItemCreateResponse(OrderItemCreateResponse orig) {
        super(orig);
    }
}
