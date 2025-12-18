/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.order;

import java.util.List;
import mjp.server.ServerMJP.database.Order;
import mjp.server.responseData.CrudResponse;
import mjp.server.responseData.ResponseData;

/**
 * Class for the Order responses.
 * @author Joan Renau Valls
 */
public class OrderResponse extends ResponseData implements CrudResponse<Order> {
    String messageStatus;
    List<Order> items;
    
    OrderResponse(){};
    
    OrderResponse(String messageStatus, List<Order> items) {
        this.messageStatus = messageStatus;
        this.items = items;
    }
    
    OrderResponse(OrderResponse orig) {
        this.messageStatus = orig.messageStatus;
        this.items = orig.items;
    }
    
    public void setItems(List<Order> items) {
        this.items = items;
    }
    
    public List<Order> getItems() {
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
