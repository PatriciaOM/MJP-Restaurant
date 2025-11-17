/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData;

/**
 * Used with TableStatusResponse, it will represent the information of one of the tables being returned.
 * @author Joan Renau Valls
 */
public class TableStatusResponseElement { // TODO must return the number also
    /**
     * The table id
     */
    private int     id;
    /**
     * The maximum amount of clients to sit on the table.
     */
    private int     maxClients;
    /**
     * The current amount of clients to siting on the table.
     */
    private int     clientsAmount;
    
    public TableStatusResponseElement(int id, int maxClients, int clientsAmount) {
        this.id = id;
        this.maxClients = maxClients;
        this.clientsAmount = clientsAmount;
    }
    
    public void setId(int value) { this.id = value; }
    public int getId() { return this.id; }
    
    public void setMaxClients(int value) { this.maxClients = value; }  
    public int getMaxClients() { return this.maxClients; }  
    
    public void setClientsAmount(int value) { this.clientsAmount = value; }  
    public int getClientsAmount() { return this.clientsAmount; }  
    
}
