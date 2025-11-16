/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.dish;

import mjp.server.queryData.table.*;
import mjp.server.queryData.user.*;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.ServerMJP.database.DishRepository;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.InfoData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author twiki
 */
public class DishGetInfo extends InfoData implements AuthorizedQueryInfo<Long> {    



    public enum SearchType{
        ALL,
        BY_ID,
        BY_NAME
    }
    private String sessionToken;
    private Dish dish;
    private SearchType searchType;
    private Long id;
    private String name;
     

   public DishGetInfo(){}
   
     /**
     * Constructor for getting a Dish by Id. I takes two parameters the session token and the Id.
     * @param sessionToken
     * @param id 
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
