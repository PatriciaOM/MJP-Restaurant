package com.mjprestaurant.model.session;

import java.util.List;

public class SessionServiceResponse {
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

    
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    
    public String getMessageStatus() {
        return this.messageStatus;
    }

    
    public void setMessageData(List data) {
        this.setSessionServices(data);
    }

    
    public List getMessageData() {
        return this.getSessionServices();
    }
}
