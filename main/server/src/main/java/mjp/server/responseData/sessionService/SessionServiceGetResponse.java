/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.sessionService;

import java.util.List;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.responseData.CrudResponse;

/**
 * Class for the SessionService get responses.
 * @author Joan Renau Valls
 */
public class SessionServiceGetResponse extends SessionServiceResponse{
    
    public SessionServiceGetResponse(){}
    
    public SessionServiceGetResponse(String messageStatus, List<SessionService> sessionService) {
        super(messageStatus, sessionService);
    }
      
    public SessionServiceGetResponse(SessionServiceGetResponse orig) {
        super(orig);
    }
}
