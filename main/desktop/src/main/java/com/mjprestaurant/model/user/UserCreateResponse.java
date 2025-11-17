package com.mjprestaurant.model.user;

/**
 * Classe que retorna la informació del servidor dels usuaris
 * @author Patricia Oliva
 */
public class UserCreateResponse {
    public String message="success";
    public User user;
    
    /**
     * Constructor per defecte
     */
    public UserCreateResponse(){
    }
    
    /**
     * Constructor principal
     * @param message missatge de retorn d'èxit
     * @param user usuari creat
     */
    public UserCreateResponse(String message, User user){
        this.message = message;
        this.user = user;
    }
    
    /**
     * Constructor que rep només l'usuari creat
     * @param user usuari creat
     */
    public UserCreateResponse(User user){
        this.user = user;
    }
    
    /**
     * Retorna l'usuari creat
     * @return usuari creat
     */
    public User getUser(){
        return this.user;
    }
    
    /**
     * Inicialitza l'usuari creat
     * @param value usuari
     */
    public void setUser(User value){
        this.user = value;
    }


}
