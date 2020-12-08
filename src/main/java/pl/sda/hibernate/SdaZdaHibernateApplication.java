package pl.sda.hibernate;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SdaZdaHibernateApplication {
    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(SdaZdaHibernateApplication.class, args);
        HibernateApp.mainOld(applicationContext);
    }
}
