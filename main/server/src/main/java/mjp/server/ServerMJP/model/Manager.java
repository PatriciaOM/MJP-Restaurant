/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import java.util.ArrayList;
import java.util.List;
import mjp.server.ServerMJP.database.DatabaseEntry;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.InfoData;
import mjp.server.responseData.CrudResponse;
import mjp.server.responseData.ResponseData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for handling the requests and responses for a specific Table. Just gets the deserialized request objects and generates the response objects that will be serialized and returned.
 * @author Joan Renau Valls
 * @param <ItemsType> The table class this Manager will be responsible for.
 * @param <RepositoryType>
 * @param <GetInfoType>
 */
@Component
//public abstract class Manager<ItemsType, RepositoryType extends CrudRepository, GetInfoType > {
public abstract class Manager<
            ItemsType extends DatabaseEntry,
            RepositoryType extends CrudRepository<ItemsType, Long>,
            GetInfoType extends InfoData & AuthorizedQueryInfo
        > {
    /** 
     * 
     * @return The session manager responsible of handling the logged sessions
     */
    protected abstract SessionManager getSessionManager();
    /**
     * 
     * @return The database repository that handles the queries of the database table class associated with this manager
     */
    protected abstract RepositoryType getRepository();
        
    
//    public abstract <InfoDataType extends InfoData> List<ItemsType> findAllItems(RepositoryType respoistory, InfoDataType infoData);
    public abstract List<ItemsType> findItems(RepositoryType respoistory, GetInfoType infoData);
    
    /**
     * Returns is a session token is allowed to make create requests
     * @param sessionToken
     * @return true if allowed
     */
    protected boolean checkCreatePermisions(String sessionToken) {
        return this.getSessionManager().validateAdminToken(sessionToken);
    }
    
    /**
     * Returns is a session token is allowed to make get requests
     * @param sessionToken
     * @return true if allowed
     */
    protected boolean checkGetPermisions(String sessionToken) {
        return this.getSessionManager().validateUserToken(sessionToken);
    }
    
    /**
     * Returns is a session token is allowed to make update requests
     * @param sessionToken
     * @return true if allowed
     */
    protected boolean checkUpdatePermisions(String sessionToken) {
        return this.getSessionManager().validateAdminToken(sessionToken);
    }
    
    /**
     * Returns is a session token is allowed to make Delete requests
     * @param sessionToken
     * @return true if allowed
     */
    protected boolean checkDeletePermisions(String sessionToken) {
        return this.getSessionManager().validateAdminToken(sessionToken);
    }
    
    /**
     * validates the data of a create request
     * @return 
     */
    protected <Info extends InfoData & AuthorizedQueryInfo<ItemsType>> boolean createValidations(Info info) {return true;}
    
    
    /**
     * validates the data of a create request
     * @return 
     */
    protected <Info extends InfoData & AuthorizedQueryInfo<ItemsType>> boolean updateValidations(Info info) {return true;}
    
    /**
     * Handles the create requests
     * @param <Info> Contains the request information after being deserialized
     * @param <ReturnType> The type of the Class that will hold the returned information before being serialized
     * @param info
     * @param role
     * @param response An empty resposne for this request that will be populated and returned by this function
     * @return Contains the response information before being serialized
     */
    public <
        Info extends InfoData & AuthorizedQueryInfo<ItemsType>,
        ReturnType extends ResponseData & CrudResponse
    >
    ReturnType create(Info info, UserRole role, ReturnType response){
        if (info.getSessionToken() == null || info.getMessageData() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            
        if (!checkCreatePermisions(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        createValidations(info);
        ItemsType data = info.getMessageData();
//        List<MessageDataType> existingEntries = getRepository().findById(data.getId());
//        if(tables.size() != 0)
//            throw new ResponseStatusException(HttpStatus.LOCKED);
        this.getRepository().save(data);
        response.setMessageStatus("Success");
        response.setMessageData(List.of(data));
        return response;
    } 
    /**
     * Handles the get requests
     * @param <ReturnType> The type of the Class that will hold the returned information before being serialized
     * @param info
     * @param response An empty response for this request that will be populated and returned by this function
     * @return Contains the response information before being serialized
     */
    public <ReturnType extends ResponseData & CrudResponse>
    ReturnType get(GetInfoType info, ReturnType response){
        if (info.getSessionToken() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!checkGetPermisions(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        List<ItemsType> items = this.findItems(this.getRepository(), info);

        response.setMessageStatus("Success");
        response.setMessageData(items);
        return response;
    }
    
    /**
     * Utility function to convert from Iterable toList
     * @param iterable
     * @return 
     */
    List<ItemsType> convertIterableToList(Iterable<ItemsType> iterable) {
        List<ItemsType> ret = new ArrayList();
        for (ItemsType item : iterable){
            ret.add(item);
        }
        return ret;
    }
    
    
    
    
    
    /**
     * Handles the update requests
     * @param <Info> The type of the request info it corresponds to a table class.
     * @param <ReturnType> The type of the Class that will hold the returned information before being serialized.
     * @param info The request info.
     * @param role 
     * @param response An empty response for this request that will be populated and returned by this function.
     * @return Contains the response information before being serialized.
     */
    public <
        Info extends InfoData & AuthorizedQueryInfo<ItemsType>,
        ReturnType extends ResponseData & CrudResponse
    >  ReturnType update(Info info, UserRole role, ReturnType response) {
        if (info.getSessionToken() == null || info.getMessageData() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (!checkUpdatePermisions(info.getSessionToken()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        updateValidations(info);
        ItemsType dataEntry = info.getMessageData();
        if(dataEntry.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        dataEntry = this.getRepository().save(dataEntry);
        response.setMessageData(List.of(dataEntry));
        return response;
    }
     
    /**
     * Handles the delete requests
     * @param <Info> The type of the request info it corresponds to a table class.
     * @param <ReturnType> The type of the Class that will hold the returned information before being serialized.
     * @param info The request info.
     * @param role 
     * @param response An empty response for this request that will be populated and returned by this function.
     * @return Contains the response information before being serialized.
     */
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
    }   
}
