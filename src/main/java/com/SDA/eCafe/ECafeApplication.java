package com.SDA.eCafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class ECafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECafeApplication.class, args);
	}
    
	@GetMapping("/")
    public String home() {
        return "index";
    }
}