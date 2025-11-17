/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.dish;


import mjp.server.ServerMJP.database.Dish;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.InfoData;

/**
 * Class for holding the information of a dish get request
 * @author Joan Renau Valls
 */
public class DishGetInfo extends InfoData implements AuthorizedQueryInfo<Long> {  
    /**
     * Represents the different search methods for this endpoint.
     */
    public enum SearchType{
        ALL,
        BY_ID,
        BY_NAME
    }
    /**
     * A valid session token with the required permissions.
     */
    private String sessionToken;
    private Dish dish;
    private SearchType searchType;
    private Long id;
    private String name;
     

   public DishGetInfo(){}
   
     /**
     * Constructor for getting a Dish by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     */
    public DishGetInfo(String sessionToken) {
        this.sessionToken = sessionToken;
        this.searchType = SearchType.ALL;
    }
   
   
     /**
     * Constructor for getting a Dish by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     * @param id 
     */
    public DishGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
    
    /**
     * Constructor for getting a Dish by name. I takes two parameters the session token and the name.
     * @param sessionToken
     * @param name 
     */
    public DishGetInfo(String sessionToken, String name) {
        this.sessionToken = sessionToken;
        this.name = name;
        this.searchType = SearchType.BY_NAME;
    }
      
    public DishGetInfo(String sessionToken, Dish dish, SearchType searchType, Long id, String name) {
        this.sessionToken = sessionToken;
        this.dish = dish;
        this.searchType = searchType;
        this.id = id;
        this.name = name;
    }  
        
    public DishGetInfo(DishGetInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.dish = orig.getDish();
        this.searchType = orig.searchType;
        this.id = orig.id;
        this.name = orig.name;
    }
           
    @Override
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    @Override
    public String getSessionToken() {
        return this.sessionToken;
    }

    public Dish getDish() {
        return dish;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setMessageData(Long requestItem) {
        setId(requestItem);
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public Long getMessageData() {
        return this.getId();
    }
}
