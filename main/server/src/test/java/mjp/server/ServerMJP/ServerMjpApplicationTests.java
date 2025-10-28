package mjp.server.ServerMJP;

//import java.net.http.HttpHeaders;
import com.google.gson.Gson;
import mjp.server.queryData.LoginInfo;
import mjp.server.responseData.LoginResponse;
import org.springframework.http.HttpHeaders;
import static org.assertj.core.api.Assertions.assertThat;
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

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ServerMjpApplicationTests {

    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void greetingShouldReturnMessage()throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ hello",
                String.class)).contains("hello");
    }
    
    @Test
    void testingPost()throws Exception {
        String url = "http://localhost:" + port + "/login";
        
        LoginResponse resObj = makePostRequest(url, new LoginInfo("Twiki", "Tuki"), LoginResponse.class);
        System.out.println(String.format("Object response: %s", resObj.toString()));
    }
    
      
    
    public <Request, Response>  Response makePostRequest(String url, Request data, Class<Response> responseType){
//    public <Response>  Response makeRequest(String url, Class<Response> responseType){
        Gson gson = new Gson();
     
        String jsonBody = gson.toJson(data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String resStr = response.getBody();
        System.out.println("RESPONSE BODY :::: " + resStr);
        assertThat(resStr).contains("user");
        assertThat(resStr).contains("token");
        assertThat(resStr).contains("0");
        
        Response resObj;
        resObj = gson.fromJson(resStr, responseType);
//        System.out.println(String.format("Object response: %s", resObj.toString()));
        return resObj;
        
    }
}



//	@Test
//	void contextLoads() {
//            System.out.println("TESTING");
//            assert(3 == 4);
//	}