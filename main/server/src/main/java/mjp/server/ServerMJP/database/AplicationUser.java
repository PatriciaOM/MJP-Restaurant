/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import mjp.server.dataClasses.UserRole;

/**
 *
 * @author twiki
 */
@Entity
public class AplicationUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String username;
    private String role;
    private String password;
    
    protected AplicationUser() {};
    
    public AplicationUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    @Override    
    public String toString() {
        return String.format(
            "Customer[id=%d, username='%s', password='%s', role='%s']",
            id, username, password, role 
        );
    }
    
    public Long getid() {
        return id;
    }
    
    public String getFirstUsername() {
        return username;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getPassword() {
        return password;
    }
        
    
    
}
