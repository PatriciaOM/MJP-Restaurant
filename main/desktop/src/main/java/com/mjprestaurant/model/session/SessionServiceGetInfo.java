package com.mjprestaurant.model.session;

/**
 * Classe que encapsula la informació necessària per obtenir detalls d'una sessió de servei.
 * @author Patricia Oliva
 */
public class SessionServiceGetInfo {    

    /**
     * Enum que representa els diferents tipus de cerca per aquest endpoint.
     */
    public enum SearchType{
        ALL,
        BY_ID,
    }
    
    private String sessionToken;
    private SessionService sessionService;
    private SearchType searchType;
    private Long id;
     
    /** Constructor per defecte */
    public SessionServiceGetInfo(){}

    /**
     * Constructor per obtenir totes les sessions d'un usuari a partir del token.
     * @param sessionToken token de sessió
     */
    public SessionServiceGetInfo(String sessionToken) {
        this.sessionToken = sessionToken;
        this.searchType = SearchType.ALL;
    }
   
    /**
     * Constructor per obtenir una sessió concreta a partir del token i l'identificador.
     * @param sessionToken token de sessió
     * @param id identificador de la sessió
     */
    public SessionServiceGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
       
    /**
     * Constructor complet amb tots els atributs.
     * @param sessionToken token de sessió
     * @param sessionService objecte SessionService
     * @param searchType tipus de cerca
     * @param id identificador de la sessió
     */
    public SessionServiceGetInfo(String sessionToken, SessionService sessionService, SearchType searchType, Long id) {
        this.sessionToken = sessionToken;
        this.sessionService = sessionService;
        this.searchType = searchType;
        this.id = id;
    }  
        
    /**
     * Constructor de còpia.
     * @param orig objecte original a copiar
     */
    public SessionServiceGetInfo(SessionServiceGetInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.sessionService = orig.getSessionService();
        this.searchType = orig.searchType;
        this.id = orig.id;
    }
           
    /** Assigna el token de sessió */
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    /** @return el token de sessió */
    public String getSessionToken() {
        return this.sessionToken;
    }

    /** @return la sessió associada */
    public SessionService getSessionService() {
        return sessionService;
    }

    /** Assigna l'identificador de la sessió */
    public void setId(Long id) {
        this.id = id;
    }

    /** Assigna l'identificador de la sessió a partir de dades de missatge */
    public void setMessageData(Long requestItem) {
        setId(requestItem);
    }

    /** @return el tipus de cerca */
    public SearchType getSearchType() {
        return searchType;
    }

    /** @return l'identificador de la sessió */
    public Long getId() {
        return id;
    }
    
    /** @return l'identificador de la sessió a partir de dades de missatge */
    public Long getMessageData() {
        return this.getId();
    }
}
