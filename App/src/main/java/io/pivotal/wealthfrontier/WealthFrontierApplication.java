package io.pivotal.wealthfrontier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@SpringBootApplication
@EnableGemfireRepositories(basePackages = "io.pivotal.wealthfrontier.repositories")
public class WealthFrontierApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WealthFrontierApplication.class, args);
	}
}
