/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mjp.server.ServerMJP.database.OrderItem;
import mjp.server.ServerMJP.database.OrderItemRepository;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.ServerMJP.database.SessionServiceRepository;
import mjp.server.queryData.orderItem.OrderItemGetInfo;
import mjp.server.queryData.sessionService.SessionServiceGetInfo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for handling the requests and responses of the OrderItem class. Just gets the deserialized request objects and generates the response objects that will be serialized and returned.
 * @author Joan Renau Valls
 */
@Component
public class OrderItemManager extends Manager<OrderItem, OrderItemRepository, OrderItemGetInfo>{

    protected SessionManager sessionManager;
    protected OrderItemRepository respository;
    
    public OrderItemManager(
        OrderItemRepository respository,
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
    protected OrderItemRepository getRepository() {
        return this.respository;
    }
    
        
    /**
     * Will be called by Manager on a get request to get the items to return.
     * @param repository
     * @param infoData
     * @return 
     */
    @Override
    public List<OrderItem> findItems(OrderItemRepository repository, OrderItemGetInfo infoData) {
        
        List<OrderItem> ret = new ArrayList();
        Optional<OrderItem> dishResult;
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
    
    @Override
    protected boolean checkCreatePermisions(String sessionToken) {
        return this.getSessionManager().validateUserToken(sessionToken);
    }
    
    @Override
    protected boolean checkUpdatePermisions(String sessionToken) {
        return this.getSessionManager().validateUserToken(sessionToken);
    }
    
    @Override
    protected boolean checkDeletePermisions(String sessionToken) {
        return this.getSessionManager().validateUserToken(sessionToken);
    }
}
