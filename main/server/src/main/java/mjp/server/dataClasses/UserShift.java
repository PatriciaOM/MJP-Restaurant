/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.dataClasses;

/**
 *
 * @author twiki
 */
public enum UserShift {

    MORNING ("user"),
    AFTERNOON ("admin"),
    FULL_TIME ("fulltime");
    
    private final String value;
    
    private UserShift(String value) {
        this.value = value;
    }
    
    /**
     * @return Returns the corresponding {@link UserRole}
     * @throws IllegalArgumentException 
     */
    public static UserShift fromString(String value) throws IllegalArgumentException {
        for (UserShift r: UserShift.values()){
            if (value.equals(value))
                return r;
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
    
    public String getValue() {
        return this.value;
    }
        
    /**
     * @return The spring representation of this role.
     */
    @Override
    public String toString() {
        return this.getValue();
    }
    
    
}
