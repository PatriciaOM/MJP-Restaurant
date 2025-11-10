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
@Table(name = "Dish")
public class Dish implements DatabaseEntry {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;   
    
    public Dish(){};
    
    @Override
    public Long getId(){ return this.id; }
    
    public void setId(Long id) { this.id = id; }
    
}
