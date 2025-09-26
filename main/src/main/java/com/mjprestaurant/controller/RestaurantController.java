package main.java.com.mjprestaurant.controller;

public class RestaurantController {
    private final List<Hotel> hoteles;

    public HotelController() {
        hoteles = new ArrayList<>();
    }

    public void agregarHotel(Hotel hotel) {
        hoteles.add(hotel);
    }

    public List<Hotel> obtenerHoteles() {
        return new ArrayList<>(hoteles);
    }
}

