package com.mjprestaurant.model.dish;;

/**
 * Classe que genera els plats
 * @author Patricia Oliva
 */

public class Dish {
    public enum DishCategory {
        APPETIZER,
        MAIN,
        DESSERT,
        DRINK,
        OTHER
    }
    private Long id; 
    
    private String name;
    private float price;
    private String description;
    private boolean available;
    private DishCategory category;
    
    /**
     * Constructor per defecte
     */
    public Dish(){};

    /**
     * Constructor principal, amb tots els paràmetres
     * @param name nom del plat
     * @param price preu del plat
     * @param description breu descripció
     * @param available si està disponible o no
     * @param category categoria del plat
     */
    public Dish(String name, float price, String description, boolean available, DishCategory category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
        this.category = category;
    }
    
    /**
     * Constructor amb paràmetres id, nom, preu, descripció i disponible
     * @param id id del plat
     * @param name nom del plat
     * @param price preu del plat
     * @param description breu descripció
     * @param available si està disponible o no
     */
    public Dish(Long id, String name, float price, String description, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
    }
    
    /**
     * Constructor que agafa com a paràmetre el plat original
     * @param orig
     */
    public Dish(Dish orig) {
        this.id = orig.id;
        this.name = orig.name;
        this.price = orig.price;
        this.description = orig.description;
        this.available = orig.available;
        this.category = orig.category;
    }
    
    /**
     * Retorna l'id del plat
     * @return id del plat
     */
    public Long getId(){ return this.id; }
    
    /**
     * Inicialitza l'id del plat
     * @param id id del plat
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Inicialitza el nom del plat
     * @param name nom del plat
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Inicialitza el preu del plat
     * @param price preu del plat
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Inicialitza la descripció del plat
     * @param description breu descripció
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Inicialitza si el plat està disponible o no
     * @param available disponible/no
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Inicialitza la categoria del plat
     * @param category categoria del plat
     */
    public void setCategory(DishCategory category) {
        this.category = category;
    }

    /**
     * Retorna el nom del plat
     * @return nom del plat
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna el preu del plat
     * @return preu
     */
    public float getPrice() {
        return price;
    }

    /**
     * Retorna la descripció del plat
     * @return descripció
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retorna si el plat està disponible o no
     * @return available
     */
    public boolean getAvailable() {
        return available;
    }

    /**
     * Retorna la categoria
     * @return categoria del plat
     */
    public DishCategory getCategory() {
        return category;
    }

    
    

}
