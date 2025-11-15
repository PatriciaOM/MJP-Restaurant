/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mjprestaurant.model.dish;

/**
 *
 * @author Patricia Oliva
 */
public class DishGetInfo {    

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
           
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setMessageData(Dish requestItem) {
        setDish(requestItem);
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
    
    public Dish getMessageData() {
        return this.getDish();
    }
}
