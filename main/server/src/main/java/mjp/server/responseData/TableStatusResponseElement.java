/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.responseData;

/**
 *
 * @author twiki
 */
public class TableStatusResponseElement {
    private int     id;
    private int     maxClients;
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
