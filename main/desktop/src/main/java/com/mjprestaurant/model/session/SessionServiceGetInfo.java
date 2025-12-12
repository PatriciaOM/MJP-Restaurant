package com.mjprestaurant.model.session;


public class SessionServiceGetInfo {    

    /**
     * Represents the different search methods for this endpoint.
     */
    public enum SearchType{
        ALL,
        BY_ID,
    }
    
    private String sessionToken;
    private SessionService sessionService;
    private SearchType searchType;
    private Long id;
     

   public SessionServiceGetInfo(){}
   
     /**
     * Constructor for getting a SessionService by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     */
    public SessionServiceGetInfo(String sessionToken) {
        this.sessionToken = sessionToken;
        this.searchType = SearchType.ALL;
    }
   
   
     /**
     * Constructor for getting a SessionService by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     * @param id 
     */
    public SessionServiceGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
       
    public SessionServiceGetInfo(String sessionToken, SessionService sessionService, SearchType searchType, Long id) {
        this.sessionToken = sessionToken;
        this.sessionService = sessionService;
        this.searchType = searchType;
        this.id = id;
    }  
        
    public SessionServiceGetInfo(SessionServiceGetInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.sessionService = orig.getSessionService();
        this.searchType = orig.searchType;
        this.id = orig.id;
    }
           
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessageData(Long requestItem) {
        setId(requestItem);
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public Long getId() {
        return id;
    }
    
    public Long getMessageData() {
        return this.getId();
    }
}
