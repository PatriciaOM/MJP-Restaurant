/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.sessionService;

import mjp.server.ServerMJP.database.SessionService;
import mjp.server.queryData.defaults.CreateInfo;

/**
 * Class for holding the information of an SessionService create request
 * @author Joan Renau Valls
 */
public class SessionServiceCreateInfo extends CreateInfo<SessionService>{    
    
    public SessionServiceCreateInfo() {
        super();
    }  
    
    public SessionServiceCreateInfo(String sessionToken, SessionService sessionService) {
        super(sessionToken, sessionService);
    }  
}
