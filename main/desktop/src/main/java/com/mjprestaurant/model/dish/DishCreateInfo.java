
package com.mjprestaurant.model.dish;

/**
 * Classe per crear un plat que envia la informació al servidor
 * @author Patricia Oliva
 */
public class DishCreateInfo {    
    private String sessionToken;
    private Dish newEntry;

    /**
     * Constructor per defecte
     */
    public DishCreateInfo() {
    }  
    
    /**
     * Constructor principal
     * @param sessionToken token de la sessió
     * @param dish plat a crear
     */
    public DishCreateInfo(String sessionToken, Dish dish) {
        this.sessionToken = sessionToken;
        this.newEntry = dish;
    }

    /**
     * Retorna el token de la sessió
     * @return token
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * Inicialitza el token de sessió
     * @param sessionToken
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /**
     * Retorna el plat
     * @return plat
     */
    public Dish getDish() {
        return newEntry;
    }

    /**
     * Inicialitza el plat
     * @param dish plat
     */
    public void setDish(Dish dish) {
        this.newEntry = dish;
    }

    /**
     * Retorna la nova entrada a afegir
     * @return nova entrada
     */
    public Dish getNewEntry() {
        return newEntry;
    }

    /**
     * Inicialitza la nova entrada
     * @param newEntry nova entrada
     */
    public void setNewEntry(Dish newEntry) {
        this.newEntry = newEntry;
    }  
    
}