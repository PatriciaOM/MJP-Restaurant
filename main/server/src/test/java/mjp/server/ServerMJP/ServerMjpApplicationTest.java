package mjp.server.ServerMJP;

//import java.net.http.HttpHeaders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import mjp.server.ServerMJP.database.User;
import mjp.server.dataClasses.UserRole;
import mjp.server.queryData.LoginInfo;
import mjp.server.queryData.LogoutInfo;
import mjp.server.responseData.LoginResponse;
import mjp.server.uitls.serializers.LocalDateAdapter;
import org.springframework.http.HttpHeaders;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ServerMjpApplicationTest {
    private final String RESET = "\033[0m";
    private final String CYAN = "\033[36m";
    
    private final String PROTOCOL = "http";
    private final String HOST = "localhost";
    
    
    private CapturedOutput capturedOutput;
    
    Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass
    private static String sessionToken = "";

    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @BeforeEach
    void setup(CapturedOutput output) {
        this.capturedOutput = output;
    }
    
    
    @AfterEach
    void clean(CapturedOutput output) {
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println("CAPTURED OUTPUT: ");
        System.out.println(this.capturedOutput.getErr());
        System.out.println(output.getOut());
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
    }
 
    @Test
    @Order(100)
    void greetingShouldReturnMessage()throws Exception{
        printTestName("greetingShouldReturnMessage");
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/hello",
//                String.class)).contains("hello");
        String url = makeUrl("/hello");
        assertThat(this.restTemplate.getForObject(url,
                String.class)).contains("hello");
    }
    
    
    @Test
    @Order(200)
    void loginWithInvalidRequest() throws Exception {
        printTestName("loginWithInvalidRequest");
        String url = makeUrl("login");
//        String url = "http://localhost:" + port + "/login";
        ResponseEntity<String> response = makePostRequest(url, new LoginInfo("Twiki", null));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    @Test
    @Order(300)
    void loginWithInvalidCredentials() throws Exception {
        printTestName("loginWithInvalidCredentials");
        String url = makeUrl("login");
//        String url = "http://localhost:" + port + "/login";
        ResponseEntity<String> response = makePostRequest(url, new LoginInfo("Twiki", "abcde1234987yioiuu"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        
        response = makePostRequest(url, new LoginInfo("eryuwtioasdfhjk857946", "Tuki"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    @Order(400)
    void loginWithValidCredentials()throws Exception {
        printTestName("loginWithValidCredentials");
        String url = makeUrl("login");
        
//        LoginResponse
        ResponseEntity<String> response = makePostRequest(url, new LoginInfo("Twiki", "Tuki"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        LoginResponse resObj = gson.fromJson(response.getBody(), LoginResponse.class);
        assertThat(resObj.role.equals(UserRole.USER.toString()));
        assertNotNull(resObj.token);
        this.sessionToken = resObj.token;
        System.out.println(String.format("testingPost returned: %s, this.sessionToken = %s", resObj.toString(), this.sessionToken));
    }
    
    @Test
    @Order(500)
    void sessionStatusWhenLogged() throws Exception {
        printTestName("sessionStatusWhenLogged");
        String url = makeUrl("/sessionstatus?sessionToken=" + this.sessionToken);
        
        String response = this.restTemplate.getForObject(url, String.class);
        System.out.println("::::::::: ::::::::: Sessionstatus check returned: " + response);
        
        
//        ResponseEntity<String> response = makePostRequest(makeUrl("sessionstatus"), String.format("{\"sessionToken\":\"%s\"", this.sessionToken));
        
        User user = gson.fromJson(response, User.class);
        assertNotNull(user.getId());
    }
    
    @Test
    @Order(600)
    void logoutWhenLoggedIn() throws Exception {
        printTestName("logoutWhenLoggedIn");
        String url = makeUrl("logout");
        
        ResponseEntity<String> response = makePostRequest(url, new LogoutInfo(this.sessionToken));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
       
//    @Test
//    @Order(700)
//    void sessionStatusWhenLoggedOut() throws Exception {
//        printTestName("sessionStatusWhenNotLoggedOut");
//        printTestName("yess you are here");
//        ResponseEntity<String> response = restTemplate.getForEntity(makeUrl("sessionstatus/" + this.sessionToken), String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        System.out.println("The body response is: " + response.getBody());
//        User user = gson.fromJson(response.getBody(), User.class);
//        assertNull(user.getId());
//    }
    
    
    public String makeUrl(String endpoint) {
        String ret =  String.format("%s://%s:%s/%s", this.PROTOCOL, this.HOST, port, endpoint);
        System.out.println(ret);
        return ret;
    }
      
    
    public <Request>  ResponseEntity<String> makePostRequest(String url, Request data){
        String jsonBody = gson.toJson(data);
        return makePostRequest(url, jsonBody);
    }
    
    public ResponseEntity<String> makePostRequest(String url, String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response;
        
    }
       
    void printTestName(String name) {
        System.out.println(CYAN + "::: TEST ::: "+ name + RESET);
    }
    
}
