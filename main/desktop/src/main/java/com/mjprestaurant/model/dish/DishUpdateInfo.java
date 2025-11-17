package com.mjprestaurant.model.dish;

/**
 * Classe per modificar un plat i enviar les dades al servidor
 * @author Patricia Oliva
 */
public class DishUpdateInfo {   
    String sessionToken;
    Dish item; 
    
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
        this.item = dish;
    }

    /**
     * Retorna el token de sessió
     * @return token
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * Inicialitza el token de sessió
     * @param sessionToken token de sessió
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /**
     * Retorna el plat a actualitzar
     * @return plat a actualitzar
     */
    public Dish getDish() {
        return item;
    }

    /**
     * Inicialitza el plat a actualitzar
     * @param dish plat a actualitzar
     */
    public void setDish(Dish dish) {
        this.item = dish;
    }

    /**
     * Retorna l'item que es vol actualitzar
     * @return item a actualitzar
     */
    public Dish getItem() {
        return item;
    }

    /**
     * Inicialitza l'item a actualitzar
     * @param item item a actualitzar
     */
    public void setItem(Dish item) {
        this.item = item;
    } 

    
    
}
