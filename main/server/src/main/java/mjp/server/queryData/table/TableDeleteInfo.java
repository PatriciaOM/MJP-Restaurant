/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.table;

import mjp.server.queryData.AuthorizedQueryInfo;
import mjp.server.queryData.user.*;


/**
 * Class for holding the information of a Table delete request
 * @author Joan Renau Valls
 */
public class TableDeleteInfo implements AuthorizedQueryInfo{
    
    private Long id;
    private String sessionToken;
    
    public TableDeleteInfo() {
        
    }
    
    public TableDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }
    
    void setId(Long val) {
        this.id = val;
    }
    
    public Long getId() {
        return this.id;
    }
    
    @Override
    public void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }

    @Override
    public Object getMessageData() {
        return null;
    }

    @Override
    public void setMessageData(Object requestItem) {}
}
