/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.database;

import java.util.List;
import mjp.server.ServerMJP.database.SessionService.SessionServiceStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for managing SessionService table
 * @author Joan Renau Valls
 */
public interface SessionServiceRepository extends CrudRepository<SessionService, Long>{
    SessionService findById(long id);
    List<SessionService> findByStatusAndIdTable(SessionServiceStatus status, Long idTable);
    List<SessionService> findByIdTable(Long idTable);
}
