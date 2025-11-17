/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.user;

import mjp.server.ServerMJP.database.User;


/**
 * Class for holding the information of an User get request
 * @author Joan Renau Valls
 */
public class UserGetInfo {
    
    /**
     * Represents the different search methods for this endpoint.
     */
    public enum SearchType{
        ALL,
        BY_ID,
        BY_USERNAME
    }
    
    private long id = -1;
    public String username = null; 
    private String sessionToken = null;
    private SearchType searchType = SearchType.ALL;
    
    public UserGetInfo(){}
    
    /**
     * Constructor for getting all User. Just takes the session token argument.
     * @param sessionToken 
     */
    public UserGetInfo(String sessionToken){
        this.searchType = SearchType.ALL;
        this.sessionToken = sessionToken;
    }
     
    /**
     * Constructor for getting a User by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     * @param id 
     */
    public UserGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
    
    /**
     * Constructor for getting a User by username. I takes two parameters the session token and the username.
     * @param sessionToken
     * @param username 
     */
    public UserGetInfo(String sessionToken, String username) {
        this.sessionToken = sessionToken;
        this.username = username;
        this.searchType = SearchType.BY_USERNAME;
    }
    
    void setUserName(String val) {
        this.username = val;
    }
    
    public String getUserName() {
        return this.username;
    }
    
    void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public SearchType getSearchType() {
        return searchType;
    }
    
    
}
