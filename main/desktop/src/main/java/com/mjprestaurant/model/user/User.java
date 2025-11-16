package com.mjprestaurant.model.user;

import java.time.LocalDate;

/**
 * Classe per l'objecte User, amb el nom d'usuari i la contrasenya
 * @author Patricia Oliva
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

    /**
     * Constructor que fa servir el login, la contrasenya i el rol
     * @param username login 
     * @param password contrasenya
     * @param role rol
     */
    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructor complet sense id
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

    /**
     * Constructor complet amb id
     * @param username nom pel login de l'usuari
     * @param password contrasenya
     * @param role rol de l'usuari
     * @param name nom de l'usuari
     * @param surname cognom/s de l'usuari
     * @param shift torn
     * @param startDate data d'inici del contracte
     * @param endDate data de finalització (pot ser null)
     * @param dni dni de l'usuari
     * @param id id de l'usuari
     */
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

    /**
     * Retorna el rol
     * @return rol
     */
    public String getRole() {
        return role;
    }

    /**
     * Inicialitza el rol (admin o user)
     * @param role rol
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Inicialitza el login per l'usuari per autenticarse
     * @param username login que es farà servir per autenticarse
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retorna el nom de l'usuari
     * @return nom de l'usuari
     */
    public String getName() {
        return name;
    }

    /**
     * Inicialitza el nom de l'usuari
     * @param name nom de l'usuari
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna els cognoms
     * @return cognoms de l'usuari
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Inicialitza els cognoms
     * @param surname cognoms de l'usuari
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Retorna el torn de l'usuari
     * @return torn de l'usuari
     */
    public UserShift getShift() {
        return shift;
    }

    /**
     * Inicialitza el torn de l'usuari (mati, tarda o indiferent)
     * @param shift torn de l'usuari
     */
    public void setShift(UserShift shift) {
        this.shift = shift;
    }

    /**
     * Retorna la data d'inici del contracte
     * @return data d'inici
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Inicialitza la data d'inici
     * @param startDate data d'inici
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Retorna la data de finalització
     * @return data de finalització del contracte
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Inicialitza la data de finalització
     * @param endDate data de finalització
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Retorna el dni
     * @return dni de l'usuari
     */
    public String getDni() {
        return dni;
    }

    /**
     * Inicialitza el dni de l'usuari
     * @param dni dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Retorna l'id de l'usuari
     * @return id de l'usuari
     */
    public Long getId() {
        return id;
    }

    /**
     * Inicialitza l'id de l'usuari
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    
}
