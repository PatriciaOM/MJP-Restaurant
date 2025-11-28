package com.mjprestaurant.model.order;

import java.util.List;

/**
 * Classe per la resposta de la comanda
 * @author Patricia Oliva
 */
public class OrderResponse {
    String messageStatus;
    List<Order> items;
    
    /**
     * Constructor per defecte
     */
    OrderResponse(){};
    
    /**
     * Constructor que rep el missatge i la llista de comandes
     * @param messageStatus missatge d'èxit
     * @param items llistat de comandes
     */
    OrderResponse(String messageStatus, List<Order> items) {
        this.messageStatus = messageStatus;
        this.items = items;
    }
    
    /**
     * Constructor que rep l'objecte
     * @param orig objecte orderResponse
     */
    OrderResponse(OrderResponse orig) {
        this.messageStatus = orig.messageStatus;
        this.items = orig.items;
    }
    
    /**
     * Inicialitza la llista de comandes
     * @param items llistat de comandes
     */
    public void setItems(List<Order> items) {
        this.items = items;
    }
    
    /**
     * Retorna el llistat de comandes
     * @return llistat de comandes
     */
    public List<Order> getItems() {
        return this.items;
    }

    /**
     * Inicialitza el missatge de l'acció
     * @param messageStatus missatge d'èxit
     */
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * Retorna el missatge de l'acció
     * @return missatge d'èxit
     */
    public String getMessageStatus() {
        return this.messageStatus;
    }

    
    public void setMessageData(List data) {
        this.setItems(data);
    }

    public List getMessageData() {
        return this.getItems();
    }
}
