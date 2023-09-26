package com.myblog5.myblog5;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.myblog5.myblog5")
public class Myblog5Application {


	public static void main(String[] args) {

		SpringApplication.run(Myblog5Application.class, args);
	}
   @Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
   }
}
