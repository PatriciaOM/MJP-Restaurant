package com.mjprestaurant.model.user;

import java.time.LocalDate;

/**
 * Classe per l'objecte User, amb el nom d'usuari i la contrasenya
 */
public class User {
    private String username;
    private String password;
    private String role; 
    private String name;  
    private String surname;
    private UserShift shift;
    private LocalDate startDate;
    private LocalDate endDate;
    private String dni;
    private Long id;

    /**
     * Constructor bàsic
     */
    public User() {
    }

    /**
     * Constructor principal amb nom i contrasenya
     * @param username nom de l'usuari
     * @param password contrasenya de l'usuari
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructor complet
     * @param username nom pel login de l'usuari
     * @param password contrasenya
     * @param role rol de l'usuari
     * @param name nom de l'usuari
     * @param surname cognom/s de l'usuari
     * @param shift torn
     * @param startDate data d'inici del contracte
     * @param endDate data de finalització (pot ser null)
     * @param dni dni de l'usuari
     */
    public User(String username, String password, String role, String name, String surname, UserShift shift,
            LocalDate startDate, LocalDate endDate, String dni) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.shift = shift;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dni = dni;
    }

    public User(String username, String password, String role, String name, String surname, UserShift shift,
            LocalDate startDate, LocalDate endDate, String dni, Long id) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.shift = shift;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dni = dni;
        this.id = id;
    }

    /**
     * Retorna el nom de l'usuari
     * @return nom del user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Inicialitza el nom de l'usuari
     * @param username nom de l'usuari
     */
    public void setUserame(String username) {
        this.username = username;
    }

    /**
     * Retorna la contrasenya
     * @return contrasenya
     */
    public String getPassword() {
        return password;
    }

    /**
     * Inicialitza la contrasenya
     * @param password contrasenya de l'usuari
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserShift getShift() {
        return shift;
    }

    public void setShift(UserShift shift) {
        this.shift = shift;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
