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
import mjp.server.queryData.defaults.DeleteInfo;

/**
 *
 * @author twiki
 */
public class DishDeleteInfo extends DeleteInfo<Long> {    
   public DishDeleteInfo(){};
   
    public DishDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }  
        
    public DishDeleteInfo(DishDeleteInfo orig) {
        this.sessionToken = orig.sessionToken;
    }
}
