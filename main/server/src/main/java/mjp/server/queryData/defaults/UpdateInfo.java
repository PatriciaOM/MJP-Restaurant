/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.defaults;


import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.InfoData;

/**
 *
 * @author twiki
 */
public class UpdateInfo<ItemType> extends InfoData implements AuthorizedQueryInfo<ItemType> {    
    /**
     * A valid session token for the endpoint being accessed
     */
    public String sessionToken;
    /**
     * The new item data. It must have set the id in order do be able to identify the right entry on the db.
     */
    public ItemType item;
    
     

   public UpdateInfo(){};
   
    public UpdateInfo(String sessionToken, ItemType item) {
        this.sessionToken = sessionToken;
        this.item = item;
    }  
        
    public UpdateInfo(UpdateInfo<ItemType> orig) {
        this.sessionToken = orig.sessionToken;
        this.item = orig.getItem();
    }
           
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    public ItemType getItem() {
        return item;
    }

    public void setItem(ItemType item) {
        this.item = item;
    }

//    @Override
//    public void setMessageData(Dish requestItem) {
//        setDish(requestItem);
//    }
//
//    @Override
//    public Dish getMessageData() {
//        return this.getDish();
//    }

    @Override
    public void setMessageData(ItemType requestItem) {
        setItem(requestItem);
        
    }

    @Override
    public ItemType getMessageData() {
        return this.getItem();
    }
}
