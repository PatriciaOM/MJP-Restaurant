package com.mjprestaurant.model;

public enum UserRole {
    USER ("user"),
    ADMIN ("admin");
    
    private final String role;
    
    UserRole(String role) {
        this.role = role;
    }
    
    public static UserRole fromString(String roleName) throws IllegalArgumentException {
        for (UserRole r: UserRole.values()){
            if (r.role.equals(roleName))
                return r;
        }
        throw new IllegalArgumentException("Invalid role: " + roleName);
    }
    
    public String getRole() {
        return this.role;
    }
    
}
