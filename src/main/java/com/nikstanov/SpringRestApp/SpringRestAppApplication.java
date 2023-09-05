package com.nikstanov.SpringRestApp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestAppApplication.class, args);
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}
}
