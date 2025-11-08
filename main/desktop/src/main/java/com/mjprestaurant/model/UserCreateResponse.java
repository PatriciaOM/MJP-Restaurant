package com.mjprestaurant.model;

public class UserCreateResponse {
    public String message="success";
    public User user;
    
    public UserCreateResponse(){
    }
    
    /**
     * 
     * @param message Returns a log out message on success.
     */
    public UserCreateResponse(String message, User user){
        this.message = message;
        this.user = user;
    }
    
    public UserCreateResponse(User user){
        this.user = user;
    }
    
    public User getUser(){
        return this.user;
    }
    
    public void setUser(User value){
        this.user = value;
    }


}
