/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.dish;

import mjp.server.queryData.table.*;
import mjp.server.queryData.user.*;
import com.google.gson.Gson;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.InfoData;
import mjp.server.queryData.defaults.CreateInfo;

/**
 *
 * @author twiki
 */
public class DishCreateInfo extends CreateInfo<Dish>{    
//    private String sessionToken;
//    private Dish dish;
//    
//     
//
//   public DishCreateInfo(){};
    
    public DishCreateInfo() {
        super();
    }  
    
    public DishCreateInfo(String sessionToken, Dish dish) {
        super(sessionToken, dish);
    }  
    
//   
//    public DishCreateInfo(String sessionToken, Dish dish) {
//        this.sessionToken = sessionToken;
//        this.dish = dish;
//    }  
//        
//    public DishCreateInfo(DishCreateInfo orig) {
//        this.sessionToken = orig.sessionToken;
//        this.dish = orig.getDish();
//    }
//           
//    public void setSessionToken(String val) {
//        this.sessionToken = val;
//    }
//    
//    public String getSessionToken() {
//        return this.sessionToken;
//    }
//
//    public Dish getDish() {
//        return dish;
//    }
//
//    public void setDish(Dish dish) {
//        this.dish = dish;
//    }
//
//    @Override
//    public void setMessageData(Dish requestItem) {
//        setDish(requestItem);
//    }
//
//    @Override
//    public Dish getMessageData() {
//        return this.getDish();
//    }
}
