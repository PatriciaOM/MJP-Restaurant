package com.mjprestaurant.model;

/**
 * Classe per l'objecte restaurant amb el nom i la direcció
 * Falta implementar-la amb més detall
 */
public class Restaurant {
    private String nombre;
    private String direccion;

    public Restaurant(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
