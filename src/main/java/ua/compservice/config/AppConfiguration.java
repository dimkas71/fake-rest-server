package ua.compservice.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.compservice.batch.BatchLoader;

@Configuration
public class AppConfiguration {
	
	
	@Bean
	BatchLoader batchLoader() {
		return new BatchLoader();
	}
	
	@Bean
	ApplicationRunner runner() {
		return (args) -> {
		

			System.out.println("Start batching process....");
			
			//batchLoader().loadContractInfo();
			
			//batchLoader().loadCounterValues();
					
			//batchLoader().loadCounterPayments();
			//batchLoader().loadContractCredit();
			
		};
	}
	
	
}
