package com.mjprestaurant.controller;

import java.util.ArrayList;
import java.util.List;

import com.mjprestaurant.model.Restaurant;

public class RestaurantController {
    private final List<Restaurant> restaurants;

    public RestaurantController() {
        restaurants = new ArrayList<>();
    }

    public void addHotel(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public List<Restaurant> getRestaurants() {
        return new ArrayList<>(restaurants);
    }
}

