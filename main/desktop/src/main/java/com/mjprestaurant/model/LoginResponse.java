package com.mjprestaurant.model;

/**
 * Classe per la resposta del login
 * @author Patricia Oliva
 */
public class LoginResponse {
    public String token;
    public String role;
    
    /**
     * Constructor bàsic
     */
    public LoginResponse(){
    }
    
    /**
     * Constructor principal
     * @param token token de sessió
     * @param role rol de l'usuari
     */
    public LoginResponse(String token, UserRole role){
        this.token = token;
        this.role = role.getRole();
    }

    /**
     * Retorna el token de sessió
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Inicialitza el token de sessió
     * @param token id de sessió
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Retorna el rol de l'usuari loguejat (admin o user)
     * @return rol de l'usuari
     */
    public String getRole() {
        return role;
    }

    /**
     * Inicialitza el rol de l'usuari 
     * @param role rol de l'usuari (admin o user)
     */
    public void setRole(String role) {
        this.role = role;
    } 

    
}
