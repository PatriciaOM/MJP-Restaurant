/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testUtils;

/**
 *
 * @author twiki
 */
public class Credentials {
    String username;
    String password;
    String sessionToken;

    Credentials(){}

    public Credentials(String username, String password, String sessionToken) {
        this.username = username;
        this.password = password;
        this.sessionToken = sessionToken;
    }
    

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public String getSessionToken() {return sessionToken;}

    public void setUsername(String username) {this.username = username;}

    public void setPassword(String password) {this.password = password;}

    public void setSessionToken(String sessionToken) {this.sessionToken = sessionToken;}
}
    
