/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.orderItem;

import java.util.List;
import mjp.server.ServerMJP.database.OrderItem;

/**
 * Class for the OrderItem delete responses.
 * @author Joan Renau Valls
 */
public class OrderItemDeleteResponse extends OrderItemResponse{
    
    public OrderItemDeleteResponse(){}
    
    public OrderItemDeleteResponse(String messageStatus, List<OrderItem> items) {
        super(messageStatus, items);
    }
      
    public OrderItemDeleteResponse(OrderItemDeleteResponse orig) {
        super(orig);
    }
}
