package testUtils;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author twiki
 */
public class TestUtils {
//    private final String RESET = "\033[0m";
//    private final String CYAN = "\033[36m";
//    
//        
//    private String DEFAULT_PROTOCOL = "http";
//    private String DEFAULT_HOST = "localhost";
//    private String DEFAULT_PORT = "8080";
//    
//    private String PROTOCOL = DEFAULT_PROTOCOL;
//    private String HOST = DEFAULT_HOST;
//    private String PORT = DEFAULT_PORT;
//    
//    
//    
//    private CapturedOutput capturedOutput;
//    
//    
//    
//        
//       
//    Gson gson = new Gson();
//    private static String sessionToken = "";
//
//    @LocalServerPort
//    private int port;
//    
//    private TestRestTemplate restTemplate;    
//    
//    public TestUtils(){}
//    
//    public TestUtils(TestRestTemplate restTempalte, String protocol, String host, String port){
//        this.restTemplate = restTemplate;
//        this.PROTOCOL = protocol;
//        this.HOST = host;
//        this.PORT = port;
//    }
//    
//    
//    
//    
//    public TestUtils setProtocol(String value) {
//        this.PROTOCOL = value;
//        return this;
//    }
//    public TestUtils setHost(String value) {
//        this.HOST = value;
//        return this;
//    }
//    public TestUtils setPort(String value) {
//        this.PORT = value;
//        return this;
//    }
//    public TestUtils setRestTemplate(TestRestTemplate value) {
//        this.restTemplate = value;
//        return this;
//    }
//    
//    public String getProtocol() {
//        return this.PROTOCOL;
//    }
//    public String getHost() {
//        return this.HOST;
//    }
//    public String getPort() {
//        return this.PORT;
//    }
//    public TestRestTemplate getRestTempalte() {
//        return this.restTemplate;
//    }
//    
//
//    
//    
//    
//    
//    
//        
//    public String makeUrl(String endpoint) {
//        String ret =  String.format("%s://%s:%s/%s", this.PROTOCOL, this.HOST, port, endpoint);
//        System.out.println(ret);
//        return ret;
//    }
//      
//    
//    public <Request>  ResponseEntity<String> makePostRequest(String url, Request data){
//        String jsonBody = gson.toJson(data);
//        return makePostRequest(url, jsonBody);
//    }
//    
//    public ResponseEntity<String> makePostRequest(String url, String jsonBody){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        
//        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
//        
//        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//        return response;
//        
//    }
//       
//    public void printTestName(String name) {
//        System.out.println(CYAN + "::: TEST ::: "+ name + RESET);
//    }
    
}
