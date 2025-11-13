/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.dish;

import java.util.List;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.responseData.table.*;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.responseData.user.*;
import mjp.server.ServerMJP.database.User;
import mjp.server.responseData.CrudResponse;
import mjp.server.responseData.ResponseData;

/**
 *
 * @author twiki
 */
public class DishResponse extends ResponseData implements CrudResponse<Dish> {
    String messageStatus;
    List<Dish> dishes;
    
    DishResponse(){};
    
    DishResponse(String messageStatus, List<Dish> dishes) {
        this.messageStatus = messageStatus;
        this.dishes = dishes;
    }
    
    DishResponse(DishResponse orig) {
        this.messageStatus = orig.messageStatus;
        this.dishes = orig.dishes;
    }
    
    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
    
    public List<Dish> getDishes() {
        return this.dishes;
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
        this.setDishes(data);
    }

    @Override
    public List getMessageData() {
        return this.getDishes();
    }
}
