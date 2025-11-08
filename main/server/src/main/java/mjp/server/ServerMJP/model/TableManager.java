/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * 
 * @author twiki
 */
@Component
public class TableManager {

    private SessionManager sessionManager;
    TableRestaurantRepository tableRepository;
    
        
    
    public TableManager(
        TableRestaurantRepository tableRepository,
        SessionManager sessionManager
    ){
        this.tableRepository = tableRepository;
        this.sessionManager = sessionManager;
    }
    
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
                tables = List.of(tableRepository.findById(info.getId()));
                break;
            case BY_NUMBER:
                tables = tableRepository.findByNum(info.getNumber());
                assert tables.size() == 1;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }    
        TableGetResponse response = new TableGetResponse(tables);
        return response;
    }
    
        
      public List<TableRestaurant> allTables() {
        ArrayList<TableRestaurant> tables = new ArrayList();
        this.tableRepository.findAll().forEach(user -> {
            tables.add(user);
        });
        return tables;
      }
    
    public TableDeleteResponse delete(TableDeleteInfo info){
        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    } 
     
    
    public TableUpdateResponse update(TableUpdateInfo info){
        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
    } 
        
    public TableStatusResponse status(TableStatusInfo info) {
        if (info.getTableId() != null)
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        TableStatusResponse response = new TableStatusResponse();
        response.addTable(new TableStatusResponseElement(1, 6, 4));
        response.addTable(new TableStatusResponseElement(2, 4, 0));
        response.addTable(new TableStatusResponseElement(3, 2, 4));
        response.addTable(new TableStatusResponseElement(4, 6, 0));
        return response;
    }
    
}
