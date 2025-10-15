/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.dataClasses;

/**
 *
 * @author twiki
 */
public enum UserRole {
    USER ("User"),
    ADMIN ("Admin");
    
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
    
    @Override
    public String toString() {
        return this.getRole();
    }
}
