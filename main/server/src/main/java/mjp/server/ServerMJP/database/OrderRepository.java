/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author twiki
 */
public interface OrderRepository extends CrudRepository<Order, Long>{
    Order findById(long id);
}
