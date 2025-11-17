/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.sessionService;

import java.util.List;
import mjp.server.ServerMJP.database.SessionService;

/**
 * Class for the SessionService create responses.
 * @author Joan Renau Valls
 */
public class SessionServiceCreateResponse extends SessionServiceResponse {
    
    public SessionServiceCreateResponse(){}
    
    public SessionServiceCreateResponse(String messageStatus, List<SessionService> sessionService) {
        super(messageStatus, sessionService);
    }
      
    public SessionServiceCreateResponse(SessionServiceCreateResponse orig) {
        super(orig);
    }
}
