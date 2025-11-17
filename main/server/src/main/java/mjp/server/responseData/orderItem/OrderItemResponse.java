/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.orderItem;

import java.util.List;
import mjp.server.ServerMJP.database.OrderItem;
import mjp.server.responseData.CrudResponse;
import mjp.server.responseData.ResponseData;

/**
 * Class for the OrderItem responses.
 * @author Joan Renau Valls
 */
public class OrderItemResponse extends ResponseData implements CrudResponse<OrderItem> {
    String messageStatus;
    List<OrderItem> items;
    
    OrderItemResponse(){};
    
    OrderItemResponse(String messageStatus, List<OrderItem> items) {
        this.messageStatus = messageStatus;
        this.items = items;
    }
    
    OrderItemResponse(OrderItemResponse orig) {
        this.messageStatus = orig.messageStatus;
        this.items = orig.items;
    }
    
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
    
    public List<OrderItem> getItems() {
        return this.items;
    }

    @Override
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Override
    public String getMessageStatus() {
        return this.messageStatus;
    }

    @Override
    public void setMessageData(List data) {
        this.setItems(data);
    }

    @Override
    public List getMessageData() {
        return this.getItems();
    }
}
