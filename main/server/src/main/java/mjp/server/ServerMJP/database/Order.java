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
import java.time.LocalDateTime;

/**
 *
 * @author twiki
 */

@Entity
@Table(name = "OrderRestaurant")
public class Order implements DatabaseEntry<Long> {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; 
    
    public enum Status {
        OPEN,
        SENDED,
        SERVED
    }
    
    private Long idSessionService;
    private LocalDateTime date;
    private Status state;
    
    public Order(){};

    public Order(Long id, Long idSessionSrevice, LocalDateTime date, Status state) {
        this.id = id;
        this.idSessionService = idSessionSrevice;
        this.date = date;
        this.state = state;
    }
    
    public Order(Long idSessionSrevice, LocalDateTime date, Status state) {
        this.idSessionService = idSessionSrevice;
        this.date = date;
        this.state = state;
    }
    
    public Order(Order orig) {
        this.idSessionService = orig.idSessionService;
        this.date = orig.date;
        this.state = orig.state;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setIdSessionService(Long idSessionService) {
        this.idSessionService = idSessionService;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setState(Status state) {
        this.state = state;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getIdSessionService() {
        return idSessionService;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Status getState() {
        return state;
    }

    
}
