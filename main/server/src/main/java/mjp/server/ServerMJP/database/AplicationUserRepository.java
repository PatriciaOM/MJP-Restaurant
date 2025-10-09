/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Joan Renau Valls
 */
public interface AplicationUserRepository extends CrudRepository<AplicationUser, Long>{
    
    List<AplicationUser> findByUsername(String username);
    
//    List<User> findByRole(String role);
    
    AplicationUser findById(long id);
    
}
