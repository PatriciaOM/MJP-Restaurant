package com.mjprestaurant.model.order;

/**
 * Classe que encapsula la informació necessària per actualitzar una comanda.
 * Conté el token de sessió i l'objecte Order amb les dades a actualitzar.
 * @author Joan Renau Valls
 */
public class OrderUpdateInfo {  
    
    public String sessionToken;  
    public Order item;
    
    /** Constructor per defecte */
    public OrderUpdateInfo(){};
   
    /**
     * Constructor amb token de sessió i comanda a actualitzar.
     * @param sessionToken token de sessió
     * @param order objecte Order a actualitzar
     */
    public OrderUpdateInfo(String sessionToken, Order order) {
        this.sessionToken = sessionToken;
        this.item = order;
    }  
        
    /**
     * Constructor de còpia.
     * @param orig objecte original a copiar
     */
    public OrderUpdateInfo(OrderUpdateInfo orig) {
        this.sessionToken = orig.sessionToken;
        this.item = orig.getItem();
    }

    /** @return el token de sessió */
    public String getSessionToken() {
        return sessionToken;
    }

    /** Assigna el token de sessió */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /** @return l'objecte Order a actualitzar */
    public Order getItem() {
        return item;
    }

    /** Assigna l'objecte Order a actualitzar */
    public void setItem(Order item) {
        this.item = item;
    }
}
