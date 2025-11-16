package com.mjprestaurant.model.dish;

import java.util.List;

/**
 * Classe amb la informació de retorn del servidor pels plats
 * @author Patricia Oliva
 */
public class DishGetResponse {

    String messageStatus;
    List<Dish> dishes;
    
    /**
     * Constructor per defecte
     */
    public DishGetResponse(){};
    
    /**
     * Constructor principal
     * @param messageStatus missatge d'estat de la petició
     * @param dishes llista de plats que arriben del servidor
     */
    public DishGetResponse(String messageStatus, List<Dish> dishes) {
        this.messageStatus = messageStatus;
        this.dishes = dishes;
    }
    
    /**
     * Constructor que té en compte una resposta concreta
     * @param orig resposta
     */
    public DishGetResponse(DishGetResponse orig) {
        this.messageStatus = orig.messageStatus;
        this.dishes = orig.dishes;
    }
    
    /**
     * Inicialitza els plats
     * @param dishes llista de plats
     */
    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
    
    /**
     * Retorna la llista de plats
     * @return llista de plats
     */
    public List<Dish> getDishes() {
        return this.dishes;
    }

    /**
     * Inicialitza el missatge d'estat
     * @param messageStatus missatge d'estat
     */
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * Retorna el missatge d'estat
     * @return missatge
     */
    public String getMessageStatus() {
        return this.messageStatus;
    }

}
