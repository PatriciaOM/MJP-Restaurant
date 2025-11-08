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

/**
 *
 * @author twiki
 */

@Entity
@Table(name = "RestaurantTable")
public class TableRestaurant {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private int num;
    
    @Column(nullable = false)
    private int maxGuests;
    
    public TableRestaurant(){};

    public TableRestaurant(int num, int maxGuests) {
        this.num = num;
        this.maxGuests = maxGuests;
    }
    
    public Long getId() {
        return id;
    }

    public int getNum() {
        return num;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }
    
    
    
}
