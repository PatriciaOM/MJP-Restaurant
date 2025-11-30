/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mjp.server.ServerMJP.database.SessionService;
import mjp.server.ServerMJP.database.SessionService.SessionServiceStatus;
import mjp.server.ServerMJP.database.SessionServiceRepository;
import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.InfoData;
import mjp.server.queryData.sessionService.SessionServiceCreateInfo;
import mjp.server.queryData.sessionService.SessionServiceGetInfo;
import mjp.server.uitls.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for handling the requests and responses of the SessionService class. Just gets the deserialized request objects and generates the response objects that will be serialized and returned.
 * @author Joan Renau Valls
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
        
    /**
     * Will be called by Manager on a get request to get the items to return.
     * @param repository
     * @param infoData
     * @return 
     */
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
    
    @Override
    protected boolean checkCreatePermisions(String sessionToken) {
        return this.getSessionManager().validateUserToken(sessionToken);
    }
    
//    protected boolean checkGetPermisions(String sessionToken) {
//        return this.getSessionManager().validateUserToken(sessionToken);
//    }
    
    @Override
    protected boolean checkUpdatePermisions(String sessionToken) {
        return this.getSessionManager().validateUserToken(sessionToken);
    }
    
    @Override
    protected boolean checkDeletePermisions(String sessionToken) {
        return this.getSessionManager().validateUserToken(sessionToken);
    }
    
    @Override
    protected <Info extends InfoData & AuthorizedQueryInfo<SessionService>> boolean createValidations(Info info) {
        SessionService sessionService = info.getMessageData();
        Long tableId = sessionService.getIdTable();

        // Get shure that there will be just one unapid entry.

        //If it is gettign updated as PAID it will be ok
        if(sessionService.getStatus() == SessionServiceStatus.PAID)
            return true;

        // If it is going to chagne form PAID to anoder status need to check that ther is no unpaid entry already in the DB.
        List<SessionService> tableAllServices = Utils.staticConvertIterableToList(this.getRepository().findByIdTable(tableId));
        List<SessionService> tablePaidServices = Utils.staticConvertIterableToList(this.getRepository().findByStatusAndIdTable(SessionService.SessionServiceStatus.PAID, tableId));
        if (tableAllServices.size() != tablePaidServices.size())
            throw new ResponseStatusException(HttpStatus.LOCKED);
        return true;
    }
    
    @Override
    protected <Info extends InfoData & AuthorizedQueryInfo<SessionService>> boolean updateValidations(Info info) {
        SessionService sessionService = info.getMessageData();
        Long tableId = sessionService.getIdTable();
        
        // Get shure that there will be just one unapid entry.
        
        //If it is gettign updated as PAID it will be ok
        if(sessionService.getStatus() == SessionServiceStatus.PAID)
            return true;
        
        //If it is already as unpaid in the DB there is no possible error.
        Optional<SessionService> dbStatus = this.getRepository().findById(sessionService.getId());
        if(dbStatus.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        SessionService sessionServiceDb = dbStatus.get();
        if(sessionServiceDb.getStatus() != SessionServiceStatus.PAID)
            return true;
        
        // If it is going to chagne form PAID to anoder status need to check that ther is no unpaid entry already in the DB.
        List<SessionService> tableAllServices = Utils.staticConvertIterableToList(this.getRepository().findByIdTable(tableId));
        List<SessionService> tablePaidServices = Utils.staticConvertIterableToList(this.getRepository().findByStatusAndIdTable(SessionService.SessionServiceStatus.PAID, tableId));
        if (tableAllServices.size() != tablePaidServices.size())
            throw new ResponseStatusException(HttpStatus.LOCKED);
        return true;
    }
}
