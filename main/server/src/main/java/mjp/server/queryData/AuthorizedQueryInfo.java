/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData;

/**
 *
 * @author twiki
 */
public interface AuthorizedQueryInfo<RequestItem> {
    AuthorizedQueryInfo createInsance(String sessionToken, RequestItem requestItem);
    public void setSessionToken(String sessionToken);
    public String getSessionToken();
    public void setMessageData(RequestItem requestItem);
    public RequestItem getMessageData();
}
