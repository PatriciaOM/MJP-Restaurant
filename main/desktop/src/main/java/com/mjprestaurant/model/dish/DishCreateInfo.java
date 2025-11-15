
package com.mjprestaurant.model.dish;

/**
 * Classe per crear un plat que envia la informació al servidor
 * @author Patricia Oliva
 */
public class DishCreateInfo {    
    private String sessionToken;
    private Dish dish;

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
        this.dish = dish;
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
        return dish;
    }

    /**
     * Inicialitza el plat
     * @param dish plat
     */
    public void setDish(Dish dish) {
        this.dish = dish;
    }  

    
    
}