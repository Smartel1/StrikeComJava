package ru.smartel.strike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class) //exclude позволяет избавиться от исключений (хз как работает)
public class StrikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrikeApplication.class, args);
	}

}
