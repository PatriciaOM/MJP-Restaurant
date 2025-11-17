/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


/**
 * Class for defining the table of the SessionService  object and holding it's entries data.
 * A session service starts when the clients sit on the table and ends when they close the session and pay.
 * @author Joan Renau Valls
 */

@Entity
@Table(name = "SessionService")
public class SessionService implements DatabaseEntry<Long> {
    /**
     * The entry id.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; 
    
    /**
     * Class for holding the state of the Sessions service. 
     * Open when the clients arrive, closed when they finish ordering, paid when they pay
     */
    public enum SessionServiceStatus {
        OPEN,
        CLOSED,
        PAID
    }
    
    /**
     * References the table to which this SessionService belongs.
     */
    private Long idTable;
    /**
    * Will hold the table number when the entry is created.
    */
    private int numTable;
    /**
    * Will hold the table maximum clients when the entry is created.
    */
    private int maxClients;
    /**
     * The id of the waiter that is responsible of this SessionService.
     */
    private int waiterId;
    /**
     * The amount of clients sitting on the table on this SessionService.
     */
    private int clients;
    /**
     * When the SessionService is created.
     */
    private LocalDateTime startDate;
    /**
     * When the SessionService is finished.
     */
    private LocalDateTime endDate;
    /**
     * The status of the SessionService.
     */
    private SessionServiceStatus status;
    /**
     * The users ratting of the service.
     */
    private int rating;
    /**
     * A comment of the SessionService left by the user.
     */
    private String comment;
        
    public SessionService(){};

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
    
    public SessionService( Long idTable,int numTable, int maxClients, int waiterId, int clients, LocalDateTime startDate, LocalDateTime endDate, SessionServiceStatus status, int rating, String comment) {
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

    public Long getId() {
        return id;
    }

    public int getNumTable() {
        return numTable;
    }

    public Long getIdTable() {
        return idTable;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public SessionServiceStatus getStatus() {
        return status;
    }

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
    
    public void setIdTable(Long idTable) {
        this.idTable = idTable;
    }

    public void setNumTable(int numTable) {
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

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStatus(SessionServiceStatus status) {
        this.status = status;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
