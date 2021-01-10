package br.com.doars.doarsAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DoarsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoarsApiApplication.class, args);
	}

}
