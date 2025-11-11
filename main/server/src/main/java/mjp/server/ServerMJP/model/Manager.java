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
 * @param <ItemsType>
 * @param <GetInfoType>
 */
@Component
//public abstract class Manager<ItemsType, RepositoryType extends CrudRepository, GetInfoType > {
public abstract class Manager<
            ItemsType extends DatabaseEntry,
            RepositoryType extends CrudRepository<ItemsType, Long>,
            GetInfoType extends InfoData & AuthorizedQueryInfo<ItemsType>
        > {
    
    protected abstract SessionManager getSessionManager();
    protected abstract RepositoryType getRepository();
        
    
//    public abstract <InfoDataType extends InfoData> List<ItemsType> findAllItems(RepositoryType respoistory, InfoDataType infoData);
    public abstract List<ItemsType> findAllItems(RepositoryType respoistory, GetInfoType infoData);
    
    protected boolean checkCreatePermisions(String sessionToken) {
        return this.getSessionManager().validateAdminToken(sessionToken);
    }
    
    protected boolean checkGetPermisions(String sessionToken) {
        return this.getSessionManager().validateUserToken(sessionToken);
    }
    
    protected boolean checkUpdatePermisions(String sessionToken) {
        return this.getSessionManager().validateAdminToken(sessionToken);
    }
    
    protected boolean checkDeletePermisions(String sessionToken) {
        return this.getSessionManager().validateAdminToken(sessionToken);
    }
    
    public <
        Info extends InfoData & AuthorizedQueryInfo<ItemsType>,
        ReturnType extends ResponseData & CrudResponse
    >
    ReturnType create(Info info, UserRole role, ReturnType response){
        if (info.getSessionToken() == null || info.getMessageData() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            
        if (!checkCreatePermisions(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ItemsType data = info.getMessageData();
//        List<MessageDataType> existingEntries = getRepository().findById(data.getId());
//        if(tables.size() != 0)
//            throw new ResponseStatusException(HttpStatus.LOCKED);
        this.getRepository().save(data);
        response.setMessageStatus("Success");
        response.setMessageData(List.of(data));
        return response;
    } 
    
     
    public <ReturnType extends ResponseData & CrudResponse>
    ReturnType get(GetInfoType info, ReturnType response){
        if (info.getSessionToken() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!checkGetPermisions(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        List<Dish> dishes = info.findAllItems(this.getRepository());
        List<ItemsType> dishes = this.findAllItems(this.getRepository(), info);

        response.setMessageStatus("Success");
        response.setMessageData(dishes);
        return response;
    }
    
    
    
    List<ItemsType> convertIterableToList(Iterable<ItemsType> iterable) {
        List<ItemsType> ret = new ArrayList();
        for (ItemsType item : iterable){
            ret.add(item);
        }
        return ret;
    }
    
    public <
        Info extends InfoData & AuthorizedQueryInfo<ItemsType>,
        ReturnType extends ResponseData & CrudResponse
    >  ReturnType update(Info info, UserRole role, ReturnType response) {
        if (info.getSessionToken() == null || info.getMessageData() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!checkUpdatePermisions(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ItemsType dataEntry = info.getMessageData();
        if(dataEntry.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        dataEntry = this.getRepository().save(dataEntry);
        response.setMessageData(List.of(dataEntry));
        return response;
            
            
            
//        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
     
    public <
        Info extends InfoData & AuthorizedQueryInfo<Long>,
        ReturnType extends ResponseData & CrudResponse
    >  ReturnType delete(Info info, UserRole role, ReturnType response) {
        if (info.getSessionToken() == null || info.getMessageData() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!checkDeletePermisions(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!this.getRepository().existsById(info.getMessageData()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.getRepository().deleteById(info.getMessageData());
        if (this.getRepository().existsById(info.getMessageData()))
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
        response.setMessageData(List.of(info));
        return response;
//        
//        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    }
    

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
