/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.defaults;

import mjp.server.queryData.dish.*;
import mjp.server.queryData.table.*;
import mjp.server.queryData.user.*;
import com.google.gson.Gson;
import mjp.server.ServerMJP.database.DatabaseEntry;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.InfoData;

/**
 *
 * @author Joan Renau Valls
 */
public class DeleteInfo<ItemType> extends InfoData implements AuthorizedQueryInfo<ItemType> {   
    /**
     * A valid session token for the endpoint being accessed
     */
    public String sessionToken;
    /**
     * The id of the element to be deleted
     */
    public ItemType id;
    
     

   public DeleteInfo(){};
   
    public DeleteInfo(String sessionToken, ItemType id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }  
        
    public DeleteInfo(DeleteInfo orig) {
        this.sessionToken = orig.sessionToken;
//        this.id = orig.getDish();
    }
           
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    public ItemType getId() {
        return id;
    }

    public void setId(ItemType id) {
        this.id = id;
    }

//    @Override
//    public void setMessageData(Long requestItem) {
//        this.setId(requestItem);
//    }
//
//    @Override
//    public Long getMessageData() {
//  

    @Override
    public void setMessageData(ItemType requestItem) {
        this.id = requestItem;
    }

    @Override
    public ItemType getMessageData() {
        return this.id;
    }
}
