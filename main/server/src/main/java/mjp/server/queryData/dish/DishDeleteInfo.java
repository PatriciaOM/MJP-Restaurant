/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.dish;

import mjp.server.queryData.defaults.DeleteInfo;

/**
 * Class for holding the information of a dish delete request
 * @author Joan Renau Valls
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
