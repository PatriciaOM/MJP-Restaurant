package mjp.server.ServerMJP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerMjpApplication {
    private static final Logger log = LoggerFactory.getLogger(ServerMjpApplication.class);

	public static void main(String[] args) {
            System.out.println(" --- Iniciant servidor --- ");
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
            
            System.out.println(" --- Servidor iniciat --- ");
	}


//        @Bean
//        public CommandLineRunner demo(CustomerRepository repository) {
//            return (args) -> {
//               repository.save(new Customer("Jack", "Bauer")) ;
//               repository.save(new Customer("Chloe", "O'Brian")) ;
//               repository.save(new Customer("David", "Palmer")) ;
//
//
//               System.out.println("DEMOOOOOOOOOOOOOOOOOOO");
//
//               log.info("Customer found with findAll()");
//               repository.findAll().forEach(customer -> {
//                   log.info(customer.toString());
//               });
//            };
//        }
}
        

