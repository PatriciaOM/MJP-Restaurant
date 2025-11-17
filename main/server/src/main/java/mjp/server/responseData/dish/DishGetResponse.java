/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.dish;

import java.util.List;
import mjp.server.ServerMJP.database.Dish;


/**
 * Class for the Dish get responses.
 * @author Joan Renau Valls
 */
public class DishGetResponse extends DishResponse{
    
    public DishGetResponse(){}
    
    public DishGetResponse(String messageStatus, List<Dish> dishes) {
        super(messageStatus, dishes);
    }
      
    public DishGetResponse(DishGetResponse orig) {
        super(orig);
    }
}
