package com.mjprestaurant.model.table;

public class TableRestaurant {
    private Long id;
    private int num;
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
