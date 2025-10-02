package mjp.server.ServerMJP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerMjpApplication {

	public static void main(String[] args) {
            System.out.println("twiki");
//            System.out.println("Hello World!");
//            
//            Jsonable jsonable = new Jsonable("wololooo");
//            Gson gson = new Gson();
//            String json = gson.toJson(jsonable);
//            System.out.print("The json string is: " + json);
//            
//            UnknownUserResponse response = new UnknownUserResponse(true);
//            String responseJSON = gson.toJson(response);
//            System.out.print("The json string is: " + responseJSON);
            
            
            SpringApplication.run(ServerMjpApplication.class, args);
            
            System.out.println("tuki");
	}

}
