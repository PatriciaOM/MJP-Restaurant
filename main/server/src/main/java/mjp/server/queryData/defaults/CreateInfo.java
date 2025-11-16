/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.defaults;

import mjp.server.queryData.table.*;
import mjp.server.queryData.user.*;
import com.google.gson.Gson;
import mjp.server.ServerMJP.database.DatabaseEntry;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.InfoData;

/**
 *
 * @author twiki
 * @param <ItemType>
 */
public class CreateInfo <ItemType extends DatabaseEntry> extends InfoData implements AuthorizedQueryInfo<ItemType> {    
    public String sessionToken;
    public ItemType newEntry;
    
             
    public CreateInfo(CreateInfo<ItemType> orig) {
        this.sessionToken = orig.sessionToken;
        this.setEntry(orig.getEntry());
        this.newEntry = orig.getEntry();
    }

    public CreateInfo(){};
   
    public CreateInfo(String sessionToken, ItemType newEntry) {
        this.sessionToken = sessionToken;
        this.newEntry = newEntry;
    }  
     
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    public ItemType getEntry() {
        return newEntry;
    }

    public void setEntry(ItemType newEntry) {
        this.newEntry = newEntry;
    }

    @Override
    public void setMessageData(ItemType requestItem) {
        setEntry(requestItem);
    }

    @Override
    public ItemType getMessageData() {
        return this.getEntry();
    }
}
