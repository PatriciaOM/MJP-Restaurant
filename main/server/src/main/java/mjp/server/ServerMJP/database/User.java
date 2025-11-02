/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import mjp.server.dataClasses.UserRole;

/**
 * Class that defines the User data to be stored in the database. 
 * 
 * @author Joan Renau Valls
 */
@Entity
@Table(name = "ApplicationUser")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    /**
     * The user's username.
     */
    @Column(unique = true, nullable = false)
    private String username;
    /**
     * The user's password.
     */
    private String password;
    /**
     * The user's role.
     */
    private UserRole role;
    
    protected User() {};
    
    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    @Override    
    public String toString() {
        return String.format(
            "User[id=%d, username='%s', password='%s', role='%s']",
            id, username, password, role 
        );
    }
    
    public Long getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public String getPassword() {
        return password;
    }
        
    
    
}
