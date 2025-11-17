/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for managing OrderItem table
 * @author Joan Renau Valls
 */
public interface OrderItemRepository extends CrudRepository<OrderItem, Long>{
    OrderItem findById(long id);
}
