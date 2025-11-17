/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.sessionService;

import mjp.server.queryData.defaults.DeleteInfo;

/**
 * Class for holding the information of an SessionService delete request
 * @author Joan Renau Valls
 */
public class SessionServiceDeleteInfo extends DeleteInfo<Long> {    
   public SessionServiceDeleteInfo(){};
   
    public SessionServiceDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }  
        
    public SessionServiceDeleteInfo(SessionServiceDeleteInfo orig) {
        this.sessionToken = orig.sessionToken;
    }
}
