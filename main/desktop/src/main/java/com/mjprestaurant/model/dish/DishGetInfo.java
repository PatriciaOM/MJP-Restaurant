/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mjprestaurant.model.dish;

/**
 * Classe per consultar la informació dels plats
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
     

    /**
     * Constructor per defecte
     */
    public DishGetInfo(){}
   
     /**
     * Constructor per demanar tots els plats
     * @param sessionToken token de sessió
     */
    public DishGetInfo(String sessionToken) {
        this.sessionToken = sessionToken;
        this.searchType = SearchType.ALL;
    }
   
   
     /**
     * Constructor per demanar un plat pel seu id
     * @param sessionToken token de sessió
     * @param id id del plat
     */
    public DishGetInfo(String sessionToken, long id) {
        this.sessionToken = sessionToken;
        this.id = id;
        this.searchType = SearchType.BY_ID;
    }
    
    /**
     * Constructor per consultar un plat pel seu nom
     * @param sessionToken token de sessió
     * @param name nom del plat
     */
    public DishGetInfo(String sessionToken, String name) {
        this.sessionToken = sessionToken;
        this.name = name;
        this.searchType = SearchType.BY_NAME;
    }
      
    /**
     * Constructor amb tots els paràmetres
     * @param sessionToken token de sessió
     * @param dish plat
     * @param searchType tipus de cerca
     * @param id id del plat
     * @param name nom del plat
     */
    public DishGetInfo(String sessionToken, Dish dish, SearchType searchType, Long id, String name) {
        this.sessionToken = sessionToken;
        this.dish = dish;
        this.searchType = searchType;
        this.id = id;
        this.name = name;
    }  
        
    /**
     * Constructor tenint un plat de referència
     * @param orig plat original
     */
    public DishGetInfo(DishGetInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.dish = orig.getDish();
        this.searchType = orig.searchType;
        this.id = orig.id;
        this.name = orig.name;
    }
           
    /**
     * Inicialitza el token de sessió
     * @param val token
     */
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    /**
     * Retorna el token de sessió
     * @return token
     */
    public String getSessionToken() {
        return this.sessionToken;
    }

    /**
     * Retorna el plat
     * @return plat
     */
    public Dish getDish() {
        return dish;
    }

    /**
     * Inicialitza el plat
     * @param dish plat a consultar
     */
    public void setDish(Dish dish) {
        this.dish = dish;
    }

    /**
     * Inicialitza el missatge per l'usuari
     * @param requestItem
     */
    public void setMessageData(Dish requestItem) {
        setDish(requestItem);
    }

    /**
     * Retorna el tipus de cerca que es vol fer servir
     * @return tipus de cerca
     */
    public SearchType getSearchType() {
        return searchType;
    }

    /**
     * Retorna l'id del plat
     * @return id del plat
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna el nom del plat
     * @return nom del plat
     */
    public String getName() {
        return name;
    }
    
    /**
     * Retorna el missatge del servidor per l'usuari
     * @return
     */
    public Dish getMessageData() {
        return this.getDish();
    }
}
