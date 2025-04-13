package fr.codecake.airbnbclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class AirbnbCloneBackApplication {

    public static void main(String[] args) {
        // Load .env file
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("AUTH0_CLIENT_ID", dotenv.get("AUTH0_CLIENT_ID"));
        System.setProperty("AUTH0_CLIENT_SECRET", dotenv.get("AUTH0_CLIENT_SECRET"));

        SpringApplication.run(AirbnbCloneBackApplication.class, args);
    }
}