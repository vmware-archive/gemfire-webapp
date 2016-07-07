package io.pivotal.wealthfrontier.loaders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@SpringBootApplication
@EnableGemfireRepositories
public class DataLoader {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(DataLoader.class, args);

		DataObjectMapper objectMapper = context.getBean(DataObjectMapper.class);
		objectMapper.loadProductsToCustomers();
	}

}
