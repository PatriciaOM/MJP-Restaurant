package com.mjprestaurant.model.session;

import java.util.List;

public class SessionServiceGetResponse {
    private String messageStatus;
    private List<SessionService> sessionServices;
    
    public SessionServiceGetResponse(){}
    
    public SessionServiceGetResponse(String messageStatus, List<SessionService> sessionServices) {
        this.messageStatus = messageStatus;
        this.sessionServices = sessionServices;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public List<SessionService> getSessionServices() {
        return sessionServices;
    }

    public void setSessionServices(List<SessionService> sessionServices) {
        this.sessionServices = sessionServices;
    }
    
    
}
