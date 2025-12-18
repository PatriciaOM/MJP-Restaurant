package com.mjprestaurant.model.session;

import java.util.List;

/**
 * Classe que encapsula la resposta d'una consulta de sessions de servei.
 * @author Patricia Oliva
 */
public class SessionServiceGetResponse {
    
    private String messageStatus;
    private List<SessionService> sessionServices;
    
    /** Constructor per defecte */
    public SessionServiceGetResponse(){}
    
    /**
     * Constructor amb tots els atributs.
     * @param messageStatus estat del missatge
     * @param sessionServices llista de sessions de servei
     */
    public SessionServiceGetResponse(String messageStatus, List<SessionService> sessionServices) {
        this.messageStatus = messageStatus;
        this.sessionServices = sessionServices;
    }

    /** @return l'estat del missatge */
    public String getMessageStatus() {
        return messageStatus;
    }

    /** Assigna l'estat del missatge */
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    /** @return la llista de sessions de servei */
    public List<SessionService> getSessionServices() {
        return sessionServices;
    }

    /** Assigna la llista de sessions de servei */
    public void setSessionServices(List<SessionService> sessionServices) {
        this.sessionServices = sessionServices;
    }
    
}
