package com.mjprestaurant.model;

public class LoginResponse {
    public String token;
    public String role;
    
    public LoginResponse(){
    }
    
    public LoginResponse(String token, UserRole role){
        this.token = token;
        this.role = role.getRole();
    } 
}
