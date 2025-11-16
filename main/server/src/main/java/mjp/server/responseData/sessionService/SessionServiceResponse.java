/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData.sessionService;

import java.util.List;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.responseData.CrudResponse;
import mjp.server.responseData.ResponseData;

/**
 *
 * @author twiki
 */
public class SessionServiceResponse extends ResponseData implements CrudResponse<SessionService> {
    String messageStatus;
    List<SessionService> sessionServices;
    
    SessionServiceResponse(){};
    
    SessionServiceResponse(String messageStatus, List<SessionService> sessionServices) {
        this.messageStatus = messageStatus;
        this.sessionServices = sessionServices;
    }
    
    SessionServiceResponse(SessionServiceResponse orig) {
        this.messageStatus = orig.messageStatus;
        this.sessionServices = orig.sessionServices;
    }
    
    public void setSessionServices(List<SessionService> sessionServices) {
        this.sessionServices = sessionServices;
    }
    
    public List<SessionService> getSessionServices() {
        return this.sessionServices;
    }

    @Override
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Override
    public String getMessageStatus() {
        return this.messageStatus;
    }

    @Override
    public void setMessageData(List data) {
        this.setSessionServices(data);
    }

    @Override
    public List getMessageData() {
        return this.getSessionServices();
    }
}
