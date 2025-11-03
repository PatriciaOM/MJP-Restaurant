/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import mjp.server.uitls.serializers.LocalDateAdapter;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author twiki
 */
public abstract class TestDefaultClass {
    
    private static final String RESET = "\033[0m";
    private static final String CYAN = "\033[36m";
       
    private Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
    private static String sessionToken = "";
    
    String PROTOCOL = "http";
    String HOST = "localhost";
    
//    public abstract TestUtils getUtils();
    public abstract TestRestTemplate getRestTemplate();
    public abstract int getPort();
    
    public <Request>  ResponseEntity<String> makePostRequest(String url, Request data){
        String jsonBody = gson.toJson(data);
        return makePostRequest(url, jsonBody);
    }    
    
    
    public ResponseEntity<String> makePostRequest(String url, String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("Sennding: " + jsonBody);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        System.out.println("The send body is: " + request.getBody());
        
        ResponseEntity<String> response = getRestTemplate().postForEntity(url, request, String.class);
        return response;
        
    }
        
    public String makeUrl(String endpoint) {
        String ret =  String.format("%s://%s:%s/%s", this.PROTOCOL, this.HOST, getPort(), endpoint);
        System.out.println(ret);
        return ret;
    }
           
    public static void printTestName(String name) {
        System.out.println(CYAN + "::: TEST ::: "+ name + RESET);
    }
    
}
