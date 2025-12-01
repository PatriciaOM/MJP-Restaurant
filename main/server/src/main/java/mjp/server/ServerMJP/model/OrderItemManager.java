/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mjp.server.ServerMJP.database.OrderItem;
import mjp.server.ServerMJP.database.OrderItemRepository;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.ServerMJP.database.SessionServiceRepository;
import mjp.server.queryData.orderItem.OrderItemGetInfo;
import mjp.server.queryData.sessionService.SessionServiceGetInfo;
import mjp.server.uitls.serializers.LocalDateAdapter;
import mjp.server.uitls.serializers.LocalDateTimeAdapter;
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
        Optional<OrderItem> orderItemResult;
        
        Gson gson = (new GsonBuilder())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    
        System.out.println("Finding orderItems with info data: " + gson.toJson(infoData));
        switch (infoData.getSearchType()) {
            case ALL:
                return this.convertIterableToList(repository.findAll());
            case BY_ID:
                orderItemResult = repository.findById(infoData.getId());
                if (orderItemResult.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                ret = List.of(orderItemResult.get());
                break;
            case BY_ORDER_ID:
                System.out.println("It is on the right palce");
                assert infoData.getOrderId() != null;
                ret = repository.findByIdOrder(infoData.getOrderId());
                break;
            default:
                System.out.println("Throwing BAD_REQUEST");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        System.out.println("returning: " + gson.toJson(ret));
//        if (ret.size() != 1)
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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
