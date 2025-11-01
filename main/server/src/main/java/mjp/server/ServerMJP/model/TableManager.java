/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.ServerMJP.model;

import mjp.server.queryData.TableStatusInfo;
import mjp.server.responseData.TableStatusResponse;
import mjp.server.responseData.TableStatusResponseElement;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * 
 * @author twiki
 */
@Component
public class TableManager {
    
        
    public TableStatusResponse tableStatus(TableStatusInfo info) {
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
