package com.techno_market.techno_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TechnoMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechnoMarketApplication.class, args);
	}

}
