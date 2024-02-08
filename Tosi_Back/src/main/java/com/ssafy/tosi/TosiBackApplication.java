package com.ssafy.tosi;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
public class TosiBackApplication {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("ASIA/SEOUL"));
	}

	public static void main(String[] args) {
		SpringApplication.run(TosiBackApplication.class, args);
	}

}
