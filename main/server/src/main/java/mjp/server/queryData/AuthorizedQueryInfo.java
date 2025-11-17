/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData;

/**
 * Interface for InfoData that will hold an authorized request with some item with data.
 * @author Joan Renau Valls
 */
public interface AuthorizedQueryInfo<RequestItem> {
    /**
     * 
     * @param sessionToken valid session token with the required permissions by the endpoint it has been seended
     */
    public void setSessionToken(String sessionToken);
    public String getSessionToken();
    /**
     * 
     * @param requestItem The dada that is being send
     */
    public void setMessageData(RequestItem requestItem);
    public RequestItem getMessageData();
}
