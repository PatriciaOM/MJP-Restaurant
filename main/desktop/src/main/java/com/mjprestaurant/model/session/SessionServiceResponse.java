package com.mjprestaurant.model.session;

import java.util.List;

/**
 * Classe que representa la resposta d'una operació sobre sessions de servei.
 * @author Patricia Oliva
 */
public class SessionServiceResponse {
    
    String messageStatus;
    List<SessionService> sessionServices;
    
    /** Constructor per defecte */
    SessionServiceResponse(){};
    
    /**
     * Constructor amb tots els atributs.
     * @param messageStatus estat del missatge
     * @param sessionServices llista de sessions de servei
     */
    SessionServiceResponse(String messageStatus, List<SessionService> sessionServices) {
        this.messageStatus = messageStatus;
        this.sessionServices = sessionServices;
    }
    
    /**
     * Constructor de còpia.
     * @param orig objecte original a copiar
     */
    SessionServiceResponse(SessionServiceResponse orig) {
        this.messageStatus = orig.messageStatus;
        this.sessionServices = orig.sessionServices;
    }
    
    /** Assigna la llista de sessions de servei */
    public void setSessionServices(List<SessionService> sessionServices) {
        this.sessionServices = sessionServices;
    }
    
    /** @return la llista de sessions de servei */
    public List<SessionService> getSessionServices() {
        return this.sessionServices;
    }

    /** Assigna l'estat del missatge */
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    /** @return l'estat del missatge */
    public String getMessageStatus() {
        return this.messageStatus;
    }

    /** Assigna la llista de sessions a partir de dades de missatge */
    public void setMessageData(List data) {
        this.setSessionServices(data);
    }

    /** @return la llista de sessions a partir de dades de missatge */
    public List getMessageData() {
        return this.getSessionServices();
    }
}
