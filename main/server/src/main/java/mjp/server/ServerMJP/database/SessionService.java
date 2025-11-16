/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author twiki
 */

@Entity
@Table(name = "SessionService")
public class SessionService implements DatabaseEntry<Long> {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; 
    
//    public enum SessionServiceStatus {
//        OPEN,
//        CLOSED,
//        PAID
//    }
    
    private String numTable;
    private int maxClients;
    private int waiterId;
    private int clients;
    private LocalDate startDate; // TODO bad type
    private LocalDate endDate; // TODO bad type
//    private SessionServiceStatus status;
    private int rating;
    private String comment;
    
    // TODO pvirave smth image;
        
    public SessionService(){};

    public SessionService(Long id, String numTable, int maxClients, int waiterId, int clients, LocalDate startDate, LocalDate endDate,  int rating, String comment) {
        this.id = id;
        this.numTable = numTable;
        this.maxClients = maxClients;
        this.waiterId = waiterId;
        this.clients = clients;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.comment = comment;
    }
    
    public SessionService(String numTable, int maxClients, int waiterId, int clients, LocalDate startDate, LocalDate endDate,  int rating, String comment) {
        this.numTable = numTable;
        this.maxClients = maxClients;
        this.waiterId = waiterId;
        this.clients = clients;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.comment = comment;
    }
    
    public SessionService(SessionService orig) {
        this.id = orig.id;
        this.numTable = orig.numTable;
        this.maxClients = orig.maxClients;
        this.waiterId = orig.waiterId;
        this.clients = orig.clients;
        this.startDate = orig.startDate;
        this.endDate = orig.endDate;
        this.rating = orig.rating;
        this.comment = orig.comment;
    }

    public Long getId() {
        return id;
    }

    public String getNumTable() {
        return numTable;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public int getWaiterId() {
        return waiterId;
    }

    public int getClients() {
        return clients;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

//    public SessionServiceStatus getStatus() {
//        return status;
//    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setNumTable(String numTable) {
        this.numTable = numTable;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

//    public void setStatus(SessionServiceStatus status) {
//        this.status = status;
//    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
