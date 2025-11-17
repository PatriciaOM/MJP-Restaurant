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
 * Class for defining the table of the OrderItem object and holding it's entries data.
 * Represents a dish that has been ordered on this order. It will take most of it's data from Dish.
 * @author Joan Renau Valls
 */

@Entity
@Table(name = "OrderItem")
public class OrderItem implements DatabaseEntry<Long> {
    /**
     * The entry id.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; 
    
    /**
     * References the Order this item belongs to.
     */
    private Long idOrder;
    /**
     * The amount the client has asked for.
     */
    private int amount;
    /**
    * The price that will have on unit. It is the Dish.price at the moment of creation.
    */
    private float price;
    /**
    * The dish description. It is the Dish.price at the moment of creation.
    */
    private String description;
    /**
    * The dish category. It is the Dish.category at the moment of creation.
    */
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
