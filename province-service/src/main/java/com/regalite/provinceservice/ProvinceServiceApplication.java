package com.regalite.provinceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProvinceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProvinceServiceApplication.class, args);
	}

}
