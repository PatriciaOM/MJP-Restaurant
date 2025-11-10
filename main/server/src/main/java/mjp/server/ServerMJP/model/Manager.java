/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mjp.server.ServerMJP.database.DatabaseEntry;
import mjp.server.ServerMJP.database.Dish;
import mjp.server.ServerMJP.database.TableRestaurant;
import mjp.server.ServerMJP.database.TableRestaurantRepository;
import mjp.server.ServerMJP.database.UserRepository;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.TableStatusInfo;
import mjp.server.queryData.dish.DishCreateInfo;
import mjp.server.queryData.InfoData;
import mjp.server.queryData.crud.GetInfo;
import mjp.server.queryData.table.TableCreateInfo;
import mjp.server.queryData.table.TableDeleteInfo;
import mjp.server.queryData.table.TableGetInfo;
import mjp.server.queryData.table.TableUpdateInfo;
import mjp.server.responseData.CrudResponse;
import mjp.server.responseData.ResponseData;
import mjp.server.responseData.TableStatusResponse;
import mjp.server.responseData.TableStatusResponseElement;
import mjp.server.responseData.dish.DishResponse;
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
public abstract class Manager<RepositoryType extends CrudRepository> {
    
    protected abstract SessionManager getSessionManager();
    protected abstract RepositoryType getRepository();
        
    
//    public Manager(
//        TableRestaurantRepository tableRepository,
//        SessionManager sessionManager
//    ){
//        this.tableRepository = tableRepository;
//        this.sessionManager = sessionManager;
//    }
    
    public <
        MessageDataType extends DatabaseEntry,
        Info extends InfoData & AuthorizedQueryInfo<MessageDataType>,
        ReturnType extends ResponseData & CrudResponse
    >
    ReturnType create(Info info, UserRole role, ReturnType response){
        if (info.getSessionToken() == null || info.getMessageData() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            
        if (!getSessionManager().validateAdminToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        MessageDataType data = info.getMessageData();
//        List<MessageDataType> existingEntries = getRepository().findById(data.getId());
//        if(tables.size() != 0)
//            throw new ResponseStatusException(HttpStatus.LOCKED);
        this.getRepository().save(data);
        response.setMessageStatus("Success");
        response.setMessageData(List.of(data));
        return response;
    } 
    
     
    public <
        MessageDataType ,
        Info extends InfoData & GetInfo & AuthorizedQueryInfo<MessageDataType>,
        ReturnType extends ResponseData & CrudResponse
    > ReturnType get(Info info, ReturnType response){
        if (info.getSessionToken() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!getSessionManager().validateUserToken(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        List<Dish> dishes = info.findAllItems(this.getRepository());
        response.setMessageStatus("Success");
        response.setMessageData(dishes);
        return response;
    }
    
    
//        
//    public List<TableRestaurant> allTables() {
//        ArrayList<TableRestaurant> tables = new ArrayList();
//        this.tableRepository.findAll().forEach(user -> {
//            tables.add(user);
//        });
//        return tables;
//      }
//    
//    public TableDeleteResponse delete(TableDeleteInfo info){
//        if (info.getSessionToken() == null)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        if (!this.sessionManager.validateAdminToken(info.getSessionToken()))
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        
//        Optional<TableRestaurant> table = this.tableRepository.findById(info.getId());
//        if (table.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        this.tableRepository.deleteById(info.getId());
//        return new TableDeleteResponse("success");
//            
////        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
//    } 
//    
//    public TableUpdateResponse update(TableUpdateInfo info){
//        if (info.getSessionToken() == null || info.getTable() == null)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        if (!this.sessionManager.validateAdminToken(info.getSessionToken()))
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        TableRestaurant table = this.tableRepository.save(info.getTable());
//        return new TableUpdateResponse(table);
//    } 
//        
//    public TableStatusResponse status(TableStatusInfo info) {
//        if (info.getTableId() != null)
//            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
//        TableStatusResponse response = new TableStatusResponse();
//        response.addTable(new TableStatusResponseElement(1, 6, 4));
//        response.addTable(new TableStatusResponseElement(2, 4, 0));
//        response.addTable(new TableStatusResponseElement(3, 2, 4));
//        response.addTable(new TableStatusResponseElement(4, 6, 0));
//        return response;
//    }
    
}
