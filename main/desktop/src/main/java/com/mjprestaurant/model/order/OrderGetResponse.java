package com.mjprestaurant.model.order;

import java.util.List;

/**
 * Classe per la comanda enviada del servidor
 * @author Patricia Oliva
 */
public class OrderGetResponse extends OrderResponse{
    
    /**
     * Constructor per defecte
     */
    public OrderGetResponse(){}
    
    /**
     * Constructor principal
     * @param messageStatus missatge d'estat
     * @param items comandes rebudes
     */
    public OrderGetResponse(String messageStatus, List<Order> items) {
        super(messageStatus, items);
    }
      
    /**
     * Constructor que rep per paràmetre la resposta
     * @param orig resposta
     */
    public OrderGetResponse(OrderGetResponse orig) {
        super(orig);
    }
}
