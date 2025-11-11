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

/**
 *
 * @author twiki
 */
public class DishDeleteInfo extends InfoData implements AuthorizedQueryInfo<Dish> {    
    private String sessionToken;
    private Long id;
    
     

   public DishDeleteInfo(){};
   
    public DishDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }  
        
    public DishDeleteInfo(DishDeleteInfo orig) {
        this.sessionToken = orig.sessionToken;
//        this.id = orig.getDish();
    }
           
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setMessageData(Dish requestItem) {
//        setDish(requestItem);
    }

    @Override
    public Dish getMessageData() {
        return new Dish();
    }
}
