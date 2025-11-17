/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.sessionService;

import java.util.List;
import mjp.server.ServerMJP.database.SessionService;

/**
 * Class for the SessionService delete responses.
 * @author Joan Renau Valls
 */
public class SessionServiceDeleteResponse extends SessionServiceResponse{
    
    public SessionServiceDeleteResponse(){}
    
    public SessionServiceDeleteResponse(String messageStatus, List<SessionService> sessionService) {
        super(messageStatus, sessionService);
    }
      
    public SessionServiceDeleteResponse(SessionServiceDeleteResponse orig) {
        super(orig);
    }
}
