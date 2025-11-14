package com.mjprestaurant.model.user;

import java.util.List;

public class UserGetResponse {

    private List<User> users;
    
    public UserGetResponse() {
    }

    public UserGetResponse(List<User> users) {
        this.users = users;
    }
    
    void setUser(List<User> val) {
        this.users = val;
    }
    
    public List<User> getUser() {
        return this.users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    

}
