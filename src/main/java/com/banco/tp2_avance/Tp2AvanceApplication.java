package com.banco.tp2_avance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Tp2AvanceApplication {
	public static void main(String[] args) {
		SpringApplication.run(Tp2AvanceApplication.class, args);
	}
}