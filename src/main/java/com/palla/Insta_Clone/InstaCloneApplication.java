package com.palla.Insta_Clone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InstaCloneApplication {

	public static void main(String[] args) {
//		Dotenv dotenv = Dotenv.configure().load();

//		System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
//		System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
//		System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
//		System.setProperty("APP_API_KEY", dotenv.get("APP_API_KEY"));

		SpringApplication.run(InstaCloneApplication.class, args);
	}

}
