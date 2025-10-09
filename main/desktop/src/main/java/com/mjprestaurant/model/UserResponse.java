package com.mjprestaurant.model;

public class UserResponse {
    public boolean logged;
    public String role;
    
    public UserResponse(boolean logged, UserRole role){
        if (role == null)
            throw new IllegalArgumentException(String.format("UserResponse: null is not permited. Arguments: %s %s.", logged, role));
        this.logged = logged;
        this.role = role.getRole();
    }

    public UserResponse(){
        
    }
    
    public void setLogged(boolean logged){
        this.logged = logged;
    }
    
    public boolean getLogged(){
        return this.logged;
    }
     
    public void setRole(UserRole role){
        this.role = role.getRole();
    }
    
    public UserRole getRole(){
        return UserRole.fromString(this.role);
    }
        
}
