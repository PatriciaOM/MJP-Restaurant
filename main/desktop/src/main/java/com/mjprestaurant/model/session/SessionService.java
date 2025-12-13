package com.mjprestaurant.model.session;

import java.time.LocalDateTime;

/**
 * Classe que representa una sessió de servei d'una taula al restaurant.
 * @author Patricia Oliva
 */
public class SessionService {
    private Long id; 
    
    /**
     * Enumeració dels possibles estats de la sessió.
     */
    public enum SessionServiceStatus {
        OPEN,
        CLOSED, 
        PAID   
    }
    private Long idTable;
    private int numTable;
    private int maxClients;
    private int waiterId;
    private int clients;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SessionServiceStatus status;
    private int rating;
    private String comment;
    
    /**
     * Constructor per defecte.
     */
    public SessionService(){};

    /**
     * Constructor amb tots els atributs, inclòs l'identificador.
     */
    public SessionService(Long id, Long idTable, int numTable, int maxClients, int waiterId, int clients, LocalDateTime startDate, LocalDateTime endDate, SessionServiceStatus status, int rating, String comment) {
        this.id = id;
        this.idTable = idTable;
        this.numTable = numTable;
        this.maxClients = maxClients;
        this.waiterId = waiterId;
        this.clients = clients;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.rating = rating;
        this.comment = comment;
    }
    
    /**
     * Constructor sense l'identificador.
     */
    public SessionService(Long idTable,int numTable, int maxClients, int waiterId, int clients, LocalDateTime startDate, LocalDateTime endDate, SessionServiceStatus status, int rating, String comment) {
        this.idTable = idTable;
        this.numTable = numTable;
        this.maxClients = maxClients;
        this.waiterId = waiterId;
        this.clients = clients;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.rating = rating;
        this.comment = comment;
    }
    
    /**
     * Constructor de còpia.
     */
    public SessionService(SessionService orig) {
        this.id = orig.id;
        this.idTable = orig.idTable;
        this.numTable = orig.numTable;
        this.maxClients = orig.maxClients;
        this.waiterId = orig.waiterId;
        this.clients = orig.clients;
        this.startDate = orig.startDate;
        this.endDate = orig.endDate;
        this.status = orig.status;
        this.rating = orig.rating;
        this.comment = orig.comment;
    }

    /** @return l'identificador de la sessió */
    public Long getId() {
        return id;
    }

    /** @return el número de la taula */
    public int getNumTable() {
        return numTable;
    }

    /** @return l'identificador de la taula */
    public Long getIdTable() {
        return idTable;
    }

    /** @return el nombre màxim de clients */
    public int getMaxClients() {
        return maxClients;
    }

    /** @return l'identificador del cambrer */
    public int getWaiterId() {
        return waiterId;
    }

    /** @return el nombre actual de clients */
    public int getClients() {
        return clients;
    }

    /** @return la data i hora d'inici de la sessió */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /** @return la data i hora de finalització de la sessió */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /** @return l'estat actual de la sessió */
    public SessionServiceStatus getStatus() {
        return status;
    }

    /** @return la valoració de la sessió */
    public int getRating() {
        return rating;
    }

    /** @return el comentari de la sessió */
    public String getComment() {
        return comment;
    }

    /** 
     * Assigna l'identificador de la sessió.
     * @param id identificador de la sessió
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /** 
     * Assigna l'identificador de la taula.
     * @param idTable identificador de la taula
     */
    public void setIdTable(Long idTable) {
        this.idTable = idTable;
    }

    /** 
     * Assigna el número de la taula.
     * @param numTable número de la taula
     */
    public void setNumTable(int numTable) {
        this.numTable = numTable;
    }

    /** 
     * Assigna el nombre màxim de clients.
     * @param maxClients nombre màxim de clients
     */
    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    /** 
     * Assigna l'identificador del cambrer.
     * @param waiterId identificador del cambrer
     */
    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

    /** 
     * Assigna el nombre actual de clients.
     * @param clients nombre de clients
     */
    public void setClients(int clients) {
        this.clients = clients;
    }

    /** 
     * Assigna la data i hora d'inici.
     * @param startDate data i hora d'inici
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /** 
     * Assigna la data i hora de finalització.
     * @param endDate data i hora de finalització
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /** 
     * Assigna l'estat de la sessió.
     * @param status estat de la sessió
     */
    public void setStatus(SessionServiceStatus status) {
        this.status = status;
    }

    /** 
     * Assigna la valoració de la sessió.
     * @param rating valoració (per exemple, de 0 a 5)
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /** 
     * Assigna el comentari de la sessió.
     * @param comment comentari
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
