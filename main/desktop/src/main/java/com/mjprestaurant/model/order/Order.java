package com.mjprestaurant.model.order;

import java.time.LocalDateTime;

/**
 * Classe per les ordres (comandes) de les taules
 * @author Patricia Oliva
 */

public class Order {

    private Long id; 
    public enum Status {
        OPEN,
        SENDED,
        SERVED
    }
    private Long idSessionService;
    private LocalDateTime date;
    private Status state;
    
    /**
     * Constructor per defecte
     */
    public Order(){};

    /**
     * Constructor principal que rep tota la informació
     * @param id id de la comanda
     * @param idSessionSrevice id de la sessió (servei)
     * @param date data de la comanda
     * @param state estat de la comanda
     */
    public Order(Long id, Long idSessionSrevice, LocalDateTime date, Status state) {
        this.id = id;
        this.idSessionService = idSessionSrevice;
        this.date = date;
        this.state = state;
    }
    
    /**
     * Constructor que rep l'id de la sessió, la data i l'estat
     * @param idSessionSrevice id de la sessió que conté la comanda
     * @param date data de la comanda
     * @param state estat de la comanda
     */
    public Order(Long idSessionSrevice, LocalDateTime date, Status state) {
        this.idSessionService = idSessionSrevice;
        this.date = date;
        this.state = state;
    }
    
    /**
     * Constructor que rep l'objecte directament
     * @param orig comanda
     */
    public Order(Order orig) {
        this.idSessionService = orig.idSessionService;
        this.date = orig.date;
        this.state = orig.state;
    }

    /**
     * Mètode que inicialitza l'id de la comanda
     * @param id id de la comanda
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Mètode que inicialitza l'id de la sessió de la comanda
     * @param idSessionService id de la sessió de la comanda
     */
    public void setIdSessionService(Long idSessionService) {
        this.idSessionService = idSessionService;
    }

    /**
     * Inicialitza la data de la comanda
     * @param date data de la comanda
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Inicialitza l'estat de la comanda (open, sended, served)
     * @param state estat de la comanda
     */
    public void setState(Status state) {
        this.state = state;
    }

    /**
     * Retorna l'id de la comanda
     * @return id de la comanda
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna l'id de la sessió que conté la comanda
     * @return id de la sessió de la comanda
     */
    public Long getIdSessionService() {
        return idSessionService;
    }

    /**
     * Retorna la data de la comanda
     * @return data de la comanda
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Retorna l'estat de la comanda
     * @return estat de la comanda
     */
    public Status getState() {
        return state;
    }
}
