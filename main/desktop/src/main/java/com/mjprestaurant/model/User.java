package com.mjprestaurant.model;

public class User {
    private String username;
    private String password;
    private boolean isAdmin;
    
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
   /* public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUserame(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  /*  public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }*/
    
}
