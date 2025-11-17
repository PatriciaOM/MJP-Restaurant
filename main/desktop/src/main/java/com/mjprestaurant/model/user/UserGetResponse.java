package com.mjprestaurant.model.user;

import java.util.List;

/**
 * Classe que conté la resposta del servidor amb la llista d'usuaris.
 * @author Patricia Oliva
 */
public class UserGetResponse {

    private List<User> users;

    /**
     * Constructor per defecte.
     */
    public UserGetResponse() {
    }

    /**
     * Constructor principal amb la llista d'usuaris.
     * @param users llista d'usuaris
     */
    public UserGetResponse(List<User> users) {
        this.users = users;
    }

    /**
     * Inicialitza la llista d'usuaris (setter intern).
     * @param val llista d'usuaris
     */
    void setUser(List<User> val) {
        this.users = val;
    }

    /**
     * Retorna la llista d'usuaris (getter intern).
     * @return llista d'usuaris
     */
    public List<User> getUser() {
        return this.users;
    }

    /**
     * Retorna la llista d'usuaris.
     * @return llista d'usuaris
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Inicialitza la llista d'usuaris.
     * @param users llista d'usuaris
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

}
