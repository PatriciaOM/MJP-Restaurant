/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author twiki
 */
@Entity
public class Customer {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   private String firstName;
   private String lastName;
   
   protected Customer() {}
   
   public Customer(String firstName, String lastName) {
       this.firstName = firstName;
       this.lastName = lastName;
   }
   
   @Override
   public String toString() {
       return String.format(
            "Customer[id=%d, firstName='%s', lastName='%s']",
            id, firstName, lastName       
       );
   }
   
   public Long getId() {
       return id;
   }
   
   public String getFirstName() {
       return firstName;
   }
   
   public String getLastName() {
       return lastName;
   }
   
}
