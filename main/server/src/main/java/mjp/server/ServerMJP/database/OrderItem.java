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
     * References the Order this item belongs to.
     */
    private Long idDish;
    /**
     * The amount the client has asked for.
     */
    private int amount;
    /**
    * The price that will have on unit. It is the Dish.price at the moment of creation.
    */
    private float price;
    /**
    * The dish name. It is the Dish.price at the moment of creation.
    */
    private String name;
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
    
    public OrderItem(Long idOrder, Long idDish,int amount, float price, String name, String description, DishCategory category){
        this.idOrder = idOrder;
        this.idDish = idDish;
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.description = description;
        this.category = category;
    };

    public OrderItem(Long id, Long idOrder, Long idDish, int amount, float price, String name, String description, DishCategory category) {
        this.id = id;
        this.idOrder = idOrder;
        this.idDish = idDish;
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    public OrderItem(OrderItem orig) { 
        this.id = orig.id;
        this.idDish = orig.idDish;
        this.idOrder = orig.idOrder;
        this.amount = orig.amount;
        this.price = orig.price;
        this.name = orig.name;
        this.description = orig.description;
        this.category = orig.category;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public void setIdDish(Long idDish) {
        this.idDish = idDish;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public Long getIdDish() {
        return idDish;
    }

    public int getAmount() {
        return amount;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DishCategory getCategory() {
        return category;
    }


    
}
