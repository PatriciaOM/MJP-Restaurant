package com.mjprestaurant.model.session;

/**
 * Classe que encapsula la informació necessària per actualitzar una sessió de servei.
 * @author Patricia Oliva
 */
public class SessionServiceUpdateInfo {   
    
    private String sessionToken;
    private SessionService item; 
    
    /** Constructor per defecte */
    public SessionServiceUpdateInfo(){};
   
    /**
     * Constructor amb token de sessió i sessió a actualitzar.
     * @param sessionToken token de sessió
     * @param sessionService sessió de servei a actualitzar
     */
    public SessionServiceUpdateInfo(String sessionToken, SessionService sessionService) {
        this.sessionToken = sessionToken;
        this.item = sessionService;
    }

    /** @return el token de sessió */
    public String getSessionToken() {
        return sessionToken;
    }

    /** Assigna el token de sessió */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /** @return l'objecte SessionService a actualitzar */
    public SessionService getItem() {
        return item;
    }

    /** Assigna l'objecte SessionService a actualitzar */
    public void setItem(SessionService item) {
        this.item = item;
    }  
}
