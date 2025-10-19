/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.dataClasses;

/**
 * Data class used to specify the possible roles of a user.
 * @author twiki
 */
public enum UserRole {
    USER ("user"),
    ADMIN ("admin");
    
    private final String role;
    
    private UserRole(String role) {
        this.role = role;
    }
    
    /**
     * @param roleName The string representation fo the role to return
     * @return Returns the corresponding {@link UserRole}
     * @throws IllegalArgumentException 
     */
    public static UserRole fromString(String roleName) throws IllegalArgumentException {
        for (UserRole r: UserRole.values()){
            if (r.role.equals(roleName))
                return r;
        }
        throw new IllegalArgumentException("Invalid role: " + roleName);
    }
    
    /**
     * @return The spring representation of this role.
     */
    public String getRole() {
        return this.role;
    }
        
    /**
     * @return The spring representation of this role.
     */
    @Override
    public String toString() {
        return this.getRole();
    }
}
