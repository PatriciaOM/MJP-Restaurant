/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.dish;

import java.util.List;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.responseData.CrudResponse;

/**
 *
 * @author twiki
 */
public class DishCreateResponse extends DishResponse {
    
    public DishCreateResponse(){}
    
    public DishCreateResponse(String messageStatus, List<Dish> dishes) {
        super(messageStatus, dishes);
    }
      
    public DishCreateResponse(DishCreateResponse orig) {
        super(orig);
    }
}
