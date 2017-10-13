package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.demo.controller"})
@ComponentScan(basePackages = {"com.demo.service"})
@ComponentScan(basePackages = {"com.demo.configuration"})
@EntityScan(basePackages = { "com.demo.model" })
@EnableJpaRepositories(basePackages = { "com.demo.repository" })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
