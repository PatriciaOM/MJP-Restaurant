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
import java.time.LocalDate;
import mjp.server.dataClasses.UserRole;
import mjp.server.dataClasses.UserShift;

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
    
    private String name;
    
    private String surname;
    
    private UserShift shift;
    
//    private LocalDate startDate;
    public LocalDate startDate;
    
    public LocalDate endDate;
    
    public String dni;
    
    
    protected User() {};
    
    public User(
        String username,
        String password,
        UserRole role,
        String name,
        String surname,
        UserShift shift,
        LocalDate startDate,
        LocalDate endDate,
        String dni
    ) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.shift = shift;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dni = dni;
    }
        
    public User(
        long id,
        String username,
        String password,
        UserRole role,
        String name,
        String surname,
        UserShift shift,
        LocalDate startDate,
        LocalDate endDate,
        String dni
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.shift = shift;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dni = dni;
        
        
    }

    
    public User(User user){
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.role = user.role;
        this.name = user.name;
        this.surname = user.surname;
        this.shift =  user.shift;
        this.startDate =  user.startDate;
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
    
    
    public void setUsername(String value) {
        this.username = value;
    }
    
    public String getUsername() {
        return username;
    }
    
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public void setPassword(String value) {
        this.password = value;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setName(String value) {
        this.name = value;
    }
        
    public String getName() {
        return this.name;
    }  
    
    public void setSurname(String value) {
        this.surname = value;
    }
        
    public String getSurname() {
        return this.surname;
    }

    public void setShift(UserShift shift) {
        this.shift = shift;
    }
        
    public UserShift getShift() {
        return shift;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }
    
    
    
}
