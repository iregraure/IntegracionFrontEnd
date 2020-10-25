package com.jacaranda.videoRentalShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jacaranda.controller", "com.jacaranda.services"})
public class VideoRentalShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoRentalShopApplication.class, args);
	}

}
