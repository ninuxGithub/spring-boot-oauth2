package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

@SpringBootApplication
public class SpringBootOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootOauth2Application.class, args);
	}

	@Bean
	public Md5PasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();
	}

}
