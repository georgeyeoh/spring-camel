package com.spike.springcamel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ImportResource("classpath:spring/application-context.xml")
public class SpringCamelApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCamelApplication.class, args);
	}

}
