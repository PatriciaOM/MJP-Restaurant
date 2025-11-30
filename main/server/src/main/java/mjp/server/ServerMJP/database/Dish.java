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

/**
 * Class for defining the table of the object Dish and holding it's entries data.
 * @author Joan Renau Valls
 */
@Entity
@Table(name = "Dish")
public class Dish implements DatabaseEntry<Long> {
    /**
     * Class for defining the category of the Dish.
     */
    public enum DishCategory {
        APPETIZER,
        MAIN,
        DESSERT,
        DRINK,
        OTHER
    }
    
    /**
     * The entry id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; 
    
    /**
     * The dish name
     */
    private String name;
    /**
     * The dish price
     */
    private float price;
    /**
     * The dish description
     */
    private String description;
    /**
     * Determines if the dish will be available
     */
    private boolean available;
    private DishCategory category;
            
    public Dish(){};

    public Dish(String name, float price, String description, boolean available, DishCategory category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
        this.category = category;
    }
    
    public Dish(Long id, String name, float price, String description, boolean available, DishCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
        this.category = category;
    }
    
    public Dish(Dish orig) {
        this.id = orig.id;
        this.name = orig.name;
        this.price = orig.price;
        this.description = orig.description;
        this.available = orig.available;
        this.category = orig.category;
    }
    
    @Override
    public Long getId(){ return this.id; }
    
    public void setId(Long id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean getAvailable() {
        return available;
    }

    public DishCategory getCategory() {
        return category;
    }
}
