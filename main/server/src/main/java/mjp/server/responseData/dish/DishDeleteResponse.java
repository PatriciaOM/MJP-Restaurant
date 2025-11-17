/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.dish;

import java.util.List;
import mjp.server.ServerMJP.database.Dish;


/**
 * Class for the Dish Delete responses.
 * @author Joan Renau Valls
 */
public class DishDeleteResponse extends DishResponse{
    
    public DishDeleteResponse(){}
    
    public DishDeleteResponse(String messageStatus, List<Dish> dishes) {
        super(messageStatus, dishes);
    }
      
    public DishDeleteResponse(DishDeleteResponse orig) {
        super(orig);
    }
}
