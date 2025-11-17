/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.sessionService;

import mjp.server.ServerMJP.database.SessionService;
import mjp.server.queryData.defaults.UpdateInfo;

/**
 * Class for holding the information of an SessionService update request
 * @author Joan Renau Valls
 */
public class SessionServiceUpdateInfo extends UpdateInfo<SessionService>  {    
    
    public SessionServiceUpdateInfo(){};
   
    public SessionServiceUpdateInfo(String sessionToken, SessionService sessionService) {
        this.sessionToken = sessionToken;
        this.item = sessionService;
    }  
        
    public SessionServiceUpdateInfo(SessionServiceUpdateInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.item = orig.getItem();
    }
}
