package com.jitsi.jitsi_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class JitsiBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(JitsiBackendApplication.class, args);
	}
	@Bean
	public ConcurrentHashMap<String, String> tokenStore() {
		return new ConcurrentHashMap<>();
	}

}
