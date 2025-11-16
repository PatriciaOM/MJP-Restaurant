/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.ServerMJP.database.DishRepository;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.ServerMJP.database.SessionServiceRepository;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.TableRestaurantRepository;
import mjp.server.ServerMJP.database.UserRepository;
import mjp.server.queryData.InfoData;
import mjp.server.queryData.TableStatusInfo;
import mjp.server.queryData.dish.DishCreateInfo;
import mjp.server.queryData.dish.DishGetInfo;
import static mjp.server.queryData.dish.DishGetInfo.SearchType.ALL;
import static mjp.server.queryData.dish.DishGetInfo.SearchType.BY_ID;
import static mjp.server.queryData.dish.DishGetInfo.SearchType.BY_NAME;
import mjp.server.queryData.sessionService.SessionServiceGetInfo;
import mjp.server.queryData.table.TableCreateInfo;
import mjp.server.queryData.table.TableDeleteInfo;
import mjp.server.queryData.table.TableGetInfo;
import mjp.server.queryData.table.TableUpdateInfo;
import mjp.server.responseData.TableStatusResponse;
import mjp.server.responseData.TableStatusResponseElement;
import mjp.server.responseData.table.TableCreateResponse;
import mjp.server.responseData.table.TableDeleteResponse;
import mjp.server.responseData.table.TableGetResponse;
import mjp.server.responseData.table.TableUpdateResponse;
import mjp.server.responseData.user.UserCreateResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * 
 * @author twiki
 */
@Component
public class SessionServiceManager extends Manager<SessionService, SessionServiceRepository, SessionServiceGetInfo>{

    protected SessionManager sessionManager;
    protected SessionServiceRepository respository;
    
    public SessionServiceManager(
        SessionServiceRepository respository,
        SessionManager sessionManager
    ){
        this.respository = respository;
        this.sessionManager = sessionManager;
    }

    @Override
    protected SessionManager getSessionManager() {
        return this.sessionManager;
    }

    @Override
    protected SessionServiceRepository getRepository() {
        return this.respository;
    }
    
    @Override
    public List<SessionService> findItems(SessionServiceRepository repository, SessionServiceGetInfo infoData) {
        
        List<SessionService> ret = new ArrayList();
        Optional<SessionService> dishResult;
        switch (infoData.getSearchType()) {
            case ALL:
                return this.convertIterableToList(repository.findAll());
            case BY_ID:
                dishResult = repository.findById(infoData.getId());
                if (dishResult.isEmpty())
                    ret = new ArrayList<>();
                else
                    ret = List.of(dishResult.get());
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (ret.size() != 1)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ret;
    }
}
