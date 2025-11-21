package com.mjprestaurant.model.table;

/**
 * Used with TableStatusResponse, it will represent the information of one of the tables being returned.
 * @author Joan Renau Valls
 */
public class TableStatusResponseElement { // TODO must return the number also
    /**
     * The table id
     */
    private Long     id;
    /**
     * The maximum amount of clients to sit on the table.
     */
    private int     maxClients;
    /**
     * The current amount of clients to siting on the table.
     */
    private int     clientsAmount;
    
    public TableStatusResponseElement(Long id, int maxClients, int clientsAmount) {
        this.id = id;
        this.maxClients = maxClients;
        this.clientsAmount = clientsAmount;
    }
    
    public void setId(Long value) { this.id = value; }
    public Long getId() { return this.id; }
    
    public void setMaxClients(int value) { this.maxClients = value; }  
    public int getMaxClients() { return this.maxClients; }  
    
    public void setClientsAmount(int value) { this.clientsAmount = value; }  
    public int getClientsAmount() { return this.clientsAmount; }  
    
}
