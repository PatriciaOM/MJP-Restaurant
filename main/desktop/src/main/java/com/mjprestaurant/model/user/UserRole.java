package com.mjprestaurant.model.user;

/**
 * Classe enum pels rols dels tipus d'usuari
 * @author Patricia Oliva
 */
public enum UserRole {
    USER ("user"),
    ADMIN ("admin");
    
    private final String role;
    
    /**
     * Constructor principal
     * @param role rol de l'usuari
     */
    UserRole(String role) {
        this.role = role;
    }
    
    /**
     * Inicialitza i retorna el rol de l'usuari amb el paràmetre roleName. Verifica que aquest estigui dins dels vàlids
     * @param roleName rol de l'usuari
     * @return objecte de tipus UserRole amb el rol passat per paràmetre
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
     * Retorna el rol
     * @return rol
     */
    public String getRole() {
        return this.role;
    }
    
}
