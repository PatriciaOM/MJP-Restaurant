/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Joan Renau Valls
 */
public abstract class TestDefaultCrudCaller extends TestDefaultCrud {
    
    @Test
    @Order(001)
    void setup(){
        basicSetup("setup");
        assertNotNull(this.getUserCredentials().getSessionToken()); // TODO maybe delete
        assertNotNull(this.getAdminCredentials().getSessionToken()); // TODO maybe delete
    }
}
