/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.table;

import mjp.server.queryData.user.*;
import mjp.server.ServerMJP.database.User;

/**
 * Class for holding the information of a Table get request
 * @author Joan Renau Valls
 */
public class TableGetInfo {
    
    /**
     * Represents the different search methods for this endpoint.
     */
    public enum SearchType{
        ALL,
        BY_ID,
        BY_NUMBER
    }
    
    private long id = -1;
    public int number = -1; 
    private String sessionToken = null;
    private SearchType searchType = SearchType.ALL;
    
    public TableGetInfo(){}
    
    /**
     * Constructor for getting all Tables. Just takes the session token argument.
     * @param sessionToken 
     */
    public TableGetInfo(String sessionToken){
        this.searchType = SearchType.ALL;
        this.sessionToken = sessionToken;
    }
     
    /**
     * Constructor for getting a Table by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     * @param id 
     */
    public TableGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
    
    /**
     * Constructor for getting a User by number. I takes two parameters the session token and the number.
     * @param sessionToken
     */
    public TableGetInfo(String sessionToken, int number) {
        this.sessionToken = sessionToken;
        this.number = number;
        this.searchType = SearchType.BY_NUMBER;
    }
    
    
    void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
    
    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public SearchType getSearchType() {
        return searchType;
    }
     
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    void setNumber(int val) {
        this.number = val;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    
}
