
package com.mjprestaurant.model.dish;

/**
 * Classe per eliminar un plat
 * @author Patricia Oliva
 */
public class DishDeleteInfo {
    String sessionToken;
    Long id;

    /**
     * Constructor per defecte
     */
    public DishDeleteInfo(){};
   
    /**
     * Constructor principal
     * @param sessionToken token de la sessió
     * @param id id del plat a eliminar
     */
    public DishDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }  
    
    /**
     * Constructor per eliminar un plat tenint en compte un plat d'origen
     * @param orig plat original a eliminar
     */
    public DishDeleteInfo(DishDeleteInfo orig) {
        this.sessionToken = orig.sessionToken;
    }
}
