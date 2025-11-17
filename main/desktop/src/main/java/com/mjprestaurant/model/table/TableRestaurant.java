package com.mjprestaurant.model.table;

/**
 * Classe Taula
 * @author Patricia Oliva
 */
public class TableRestaurant {
    private Long id;
    private int num;
    private int maxGuests;
    
    /**
     * Constructor per defecte
     */
    public TableRestaurant(){};

    /**
     * Constructor principal
     * @param num número de taula
     * @param maxGuests màxim de comensals
     */
    public TableRestaurant(int num, int maxGuests) {
        this.num = num;
        this.maxGuests = maxGuests;
    }
    
    /**
     * Retorna l'id de la taula
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna el número de taula
     * @return num de taula
     */
    public int getNum() {
        return num;
    }

    /**
     * Retorna el màxim de comensals
     * @return max de comensals
     */
    public int getMaxGuests() {
        return maxGuests;
    }

    /**
     * Inicialitza l'id de la taula
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Inicialitza el número de taula
     * @param num numero de taula
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Inicialitza el màxim de comensals
     * @param maxGuests max de comensals
     */
    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }
}
