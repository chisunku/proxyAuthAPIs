package com.sjsu.proxyAuth;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableMongoRepositories
public class ProxyAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyAuthApplication.class, args);
	}

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
	}

}
