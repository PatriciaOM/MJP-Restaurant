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
        SpringApplication.run(ServerMjpApplication.class, args);
        System.out.println(" --- Servidor iniciat --- ");
    }
}
        

