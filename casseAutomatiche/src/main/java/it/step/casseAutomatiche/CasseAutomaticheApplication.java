package it.step.casseAutomatiche;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "it.step.casseAutomatiche.repository")
@EntityScan("it.step.casseAutomatiche.models")
public class CasseAutomaticheApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasseAutomaticheApplication.class, args);
	}

}
