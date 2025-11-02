package com.mjprestaurant.model;

import java.util.Date;

/**
 * Classe Worker
 * @author Patricia Oliva
 */
public class Worker {
    private int id;
    private String dni;
    private String name;
    private String surname;
    private Date startDate;
    private Date endDate;
    private Shift shift;

    //comptador per fer autoincremental el camp id
    private int workerCounter = 0;

    public enum Shift {
        MORNING,    
        AFTERNOON,
        FULL_TIME
    }

    /**
     * Constructor principal amb les dades necessàries pels treballadors
     * @param dni DNI del treballador
     * @param name nom del treballador
     * @param surname cognom del treballador
     * @param startDate inici del contracte
     * @param endDate finalització del contraste si escau
     * @param shift torn del treballador
     */
    public Worker(String dni, String name, String surname, Date startDate, Date endDate, Shift shift) {
        workerCounter++;
        this.id = workerCounter;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shift = shift;
    }

    /**
     * Retorna l'id del treballador
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Inicialitza el paràmetre id
     * @param id id del treballador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna el dni 
     * @return dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * Inicialitza el paràmetre pel dni
     * @param dni dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Retorna el nom del treballador
     * @return nom
     */
    public String getName() {
        return name;
    }

    /**
     * Inicialitza el nom del treballador
     * @param name nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna el cognom del treballador
     * @return cognom
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Inicialitza el cognom
     * @param surname cognom
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Retorna la data d'inici del contracte
     * @return data d'inici
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Inicialitza la data del contracte
     * @param startDate data d'inici
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Retorna la data de finalització si existeix
     * @return data de finalització
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Inicialitza la data de finalització
     * @param endDate data de finalització
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Retorna el torn del treballador
     * @return torn
     */
    public Shift getShift() {
        return shift;
    }

    /**
     * Inicialitza el torn del treballador
     * @param shift torn
     */
    public void setShift(Shift shift) {
        this.shift = shift;
    }

}
