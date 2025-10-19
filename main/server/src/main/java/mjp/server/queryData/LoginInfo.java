/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.queryData;

/**
 * This class is responsible for holding the information sent to the endpoint defined in {@link mjp.server.ServerMJP.Controller.Controller#login}
 * @author Joan Renau Valls
 */
public class LoginInfo {
    String username;
    String password;
    LoginInfo(){};
    LoginInfo(String username, String password){
        this.username = username;
        this.password = password;
    }
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
            
}
