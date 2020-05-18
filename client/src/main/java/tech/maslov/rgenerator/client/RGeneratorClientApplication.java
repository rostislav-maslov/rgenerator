package tech.maslov.rgenerator.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication(scanBasePackages = {"com.rcore", "tech.maslov.rgenerator"}, exclude = {EmbeddedMongoAutoConfiguration.class})
@EnableFeignClients(basePackages = {"tech.maslov.rgenerator.infrastructure"})
@EnableMongoRepositories
public class RGeneratorClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RGeneratorClientApplication.class, args);
    }

}
