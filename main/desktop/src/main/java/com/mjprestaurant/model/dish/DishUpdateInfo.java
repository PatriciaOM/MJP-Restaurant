package com.mjprestaurant.model.dish;

/**
 * Classe per modificar un plat i enviar les dades al servidor
 * @author Patricia Oliva
 */
public class DishUpdateInfo {   
    String sessionToken;
    Dish dish; 
    
    /**
     * Constructor per defecte
     */
    public DishUpdateInfo(){};
   
    /**
     * Constructor principal
     * @param sessionToken token de la sessió 
     * @param dish plat a modificar
     */
    public DishUpdateInfo(String sessionToken, Dish dish) {
        this.sessionToken = sessionToken;
        this.dish = dish;
    }  
}
