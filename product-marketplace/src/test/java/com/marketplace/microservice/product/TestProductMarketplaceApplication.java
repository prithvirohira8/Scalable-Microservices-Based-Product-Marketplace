package com.marketplace.microservice.product;

import org.springframework.boot.SpringApplication;

public class TestProductMarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProductMarketplaceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
