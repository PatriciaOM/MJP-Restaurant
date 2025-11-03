/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.user;

import mjp.server.ServerMJP.database.User;

/**
 *
 * @author twiki
 */
public class UserGetInfo {
    
    private static String SEARCH_ALL = "all";
    private static String SEARCH_BY_ID = "byId";
    private static String SEARCH_BY_USERNAME = "byUsername";
    
    private long id = -1;
    public String username = null; //TODO refactor getter and setter names and set to private
    private String sessionToken = null;
    private String searchType = SEARCH_ALL;
    
    public UserGetInfo(){}
    
    public UserGetInfo(String sessionToken){
        this.searchType = SEARCH_ALL;
    }
     
    public UserGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SEARCH_BY_ID;
    }
    
    public UserGetInfo(String sessionToken, String username) {
        this.sessionToken = sessionToken;
        this.username = username;
        this.searchType = SEARCH_BY_USERNAME;
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

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }
    
    
}
