/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData.table;

import mjp.server.queryData.user.*;

/**
 *
 * @author twiki
 */
public class TableDeleteInfo {
    
    private Long id;
    private String sessionToken;
    
    
    public TableDeleteInfo(String sessionToken, Long id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }
    
    void setUserId(Long val) {
        this.id = val;
    }
    
    public Long getUserId() {
        return this.id;
    }
    
    void setSessionToken(String val) {
        this.sessionToken = val;
    }
    
    public String getSessionToken() {
        return this.sessionToken;
    }
}
