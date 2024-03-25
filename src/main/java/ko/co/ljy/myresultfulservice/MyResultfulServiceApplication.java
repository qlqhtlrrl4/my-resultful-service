package ko.co.ljy.myresultfulservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MyResultfulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyResultfulServiceApplication.class, args);
    }

}
