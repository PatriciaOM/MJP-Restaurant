/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.order;

import java.util.List;
import mjp.server.ServerMJP.database.Order;

/**
 * Class for the Order delete responses.
 * @author Joan Renau Valls
 */
public class OrderDeleteResponse extends OrderResponse{
    
    public OrderDeleteResponse(){}
    
    public OrderDeleteResponse(String messageStatus, List<Order> items) {
        super(messageStatus, items);
    }
      
    public OrderDeleteResponse(OrderDeleteResponse orig) {
        super(orig);
    }
}
