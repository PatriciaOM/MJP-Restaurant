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
import mjp.server.ServerMJP.database.Dish.DishCategory;

/**
 *
 * @author twiki
 */

@Entity
@Table(name = "OrderItem")
public class OrderItem implements DatabaseEntry<Long> {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; 
    
    private Long idOrder;
    private int amount;
    private float price;
    private String description;
    private DishCategory category;
    
    
    
    public OrderItem() {
    }
    
    public OrderItem(Long idOrder, int amount, float price, String description, DishCategory category){
        this.idOrder = idOrder;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.category = category;
    };

    public OrderItem(Long id, Long idOrder, int amount, float price, String description, DishCategory category) {
        this.id = id;
        this.idOrder = idOrder;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.category = category;
    }
    
    public OrderItem(OrderItem orig) {
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    
}
