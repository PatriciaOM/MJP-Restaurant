/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for managing Dish table
 * @author Joan Renau Valls
 */
public interface DishRepository extends CrudRepository<Dish, Long>{
    Dish findById(long id);
    Dish findByname(String name);
    List<Dish> findAllByName(String name);
}
