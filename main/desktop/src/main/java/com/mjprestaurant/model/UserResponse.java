package com.mjprestaurant.model;

/**
 * Classe de l'usuari retornat pel server
 * @author Patricia Oliva
 */
public class UserResponse {
    public boolean logged;
    public String role;
    
    /**
     * Constructor principal
     * @param logged booleà que indica si el login és correcte o no
     * @param role rol de l'usuari (admin o user)
     */
    public UserResponse(boolean logged, UserRole role){
        if (role == null)
            throw new IllegalArgumentException(String.format("UserResponse: null is not permited. Arguments: %s %s.", logged, role));
        this.logged = logged;
        this.role = role.getRole();
    }

    /**
     * Constructor bàsic
     */
    public UserResponse(){
        
    }
    
    /**
     * Inicialitza el paràmetre que determina si el login és correcte o no
     * @param logged
     */
    public void setLogged(boolean logged){
        this.logged = logged;
    }
    
    /**
     * Retorna si el login és correcte o no
     * @return loguejat o no
     */
    public boolean getLogged(){
        return this.logged;
    }
     
    /**
     * Inicialitza el rol de l'usuari (admin o user)
     * @param role rol del user
     */
    public void setRole(UserRole role){
        this.role = role.getRole();
    }
    
    /**
     * Retorna el rol de l'usuari (admin o user)
     * @return admin o user
     */
    public UserRole getRole(){
        return UserRole.fromString(this.role);
    }
        
}
