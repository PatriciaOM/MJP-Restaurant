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
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.ServerMJP.database.SessionService.SessionServiceStatus;
import mjp.server.ServerMJP.database.SessionServiceRepository;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.TableRestaurantRepository;
import mjp.server.ServerMJP.database.UserRepository;
import mjp.server.queryData.TableStatusInfo;
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
import mjp.server.uitls.Utils;
import mjp.server.uitls.serializers.LocalDateAdapter;
import mjp.server.uitls.serializers.LocalDateTimeAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for handling the requests and responses of the Table class. Just gets the deserialized request objects and generates the response objects that will be serialized and returned.
 * @author Joan Renau Valls
 */
@Component
public class TableManager {

    private SessionManager sessionManager;
    TableRestaurantRepository tableRepository;
    SessionServiceRepository sessionServiceRepository;
    
        
    
    public TableManager(
        TableRestaurantRepository tableRepository,
        SessionManager sessionManager,
        SessionServiceRepository sessionServiceRepository
    ){
        this.tableRepository = tableRepository;
        this.sessionManager = sessionManager;
        this.sessionServiceRepository = sessionServiceRepository;
    }
    
    /**
     * Used to handle a create request
     * @param info
     * @return 
     */
    public TableCreateResponse create(TableCreateInfo info){
        if (info.getSessionToken() == null || info.getTable() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            
        if (!sessionManager.validateAdminToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        TableRestaurant table = info.getTable();
        List<TableRestaurant> tables = tableRepository.findByNum(table.getNum());
        if(tables.size() != 0)
            throw new ResponseStatusException(HttpStatus.LOCKED);
        this.tableRepository.save(table);
        return new TableCreateResponse(info.getTable());
    } 
    
    /**
     * 
     * Used to handle a get request
     * @param info
     * @return 
     */
    public TableGetResponse get(TableGetInfo info){
        if (info.getSessionToken() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!this.sessionManager.validateUserToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        List<TableRestaurant> tables;
        switch (info.getSearchType()){
            case ALL:
                tables = this.allTables();
                break;
            case BY_ID:
                TableRestaurant table = tableRepository.findById(info.getId());
                if (table == null)
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                tables = List.of(table);
                break;
            case BY_NUMBER:
                tables = tableRepository.findByNum(info.getNumber());
                if (tables.size() != 1)
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }    
        TableGetResponse response = new TableGetResponse(tables);
        return response;
    }
        
    private List<TableRestaurant> allTables() {
        ArrayList<TableRestaurant> tables = new ArrayList();
        this.tableRepository.findAll().forEach(user -> {
            tables.add(user);
        });
        return tables;
      }
    /**
     * Used to handle a delete request
     * @param info
     * @return 
     */
    public TableDeleteResponse delete(TableDeleteInfo info){
        if (info.getSessionToken() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!this.sessionManager.validateAdminToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        
        Optional<TableRestaurant> table = this.tableRepository.findById(info.getId());
        if (table.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.tableRepository.deleteById(info.getId());
        return new TableDeleteResponse("success");
            
//        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    } 
    
    /**
     * Used to handle a update request
     * @param info
     * @return 
     */
    public TableUpdateResponse update(TableUpdateInfo info){
        if (info.getSessionToken() == null || info.getTable() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!this.sessionManager.validateAdminToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        TableRestaurant table = this.tableRepository.save(info.getTable());
        return new TableUpdateResponse(table);
    } 
        
    /** Returns the status of the tables
     * 
     * @param info
     * @return 
     */
    public TableStatusResponse status(TableStatusInfo info) {
        if (info.getTableId() != null)
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        TableStatusResponse response = new TableStatusResponse();
        Iterable<SessionService> servicesssItter = sessionServiceRepository.findAll();// tables.get(0).getId());
        List<SessionService> servicesss = Utils.staticConvertIterableToList(servicesssItter);
        System.out.println("Session services recived: " + servicesss.size());
        
        
        Gson gson = (new GsonBuilder())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
        for (SessionService service : servicesss){
                System.out.println(String.format("Session service: %s", gson.toJson(service)));
            }
        System.out.println("========================");
        List<TableRestaurant> tables = allTables();
        for (TableRestaurant table : tables) {
            List<SessionService> services = sessionServiceRepository.findByStatusAndIdTable(SessionServiceStatus.OPEN, table.getId());// tables.get(0).getId());
            System.out.println("Queryied sessionServices with id table = " +  table.getId());
            System.out.println("Session services recived: " + services.size());
            for (SessionService service : services){
                System.out.println(String.format("Session service: %s", service));
            }
            int clientsAmmount = 0;
            Long SessionServciceId = -1L;
            if (services.size() > 0){
                clientsAmmount = services.get(0).getClients();
                SessionServciceId = services.get(0).getId();
                
            }
            response.addTable(new TableStatusResponseElement(table.getId(), table.getMaxGuests(), clientsAmmount));
            
            System.out.println("--------------------");
        }
        return response;
    }
}
