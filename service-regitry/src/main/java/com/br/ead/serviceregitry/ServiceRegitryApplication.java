package com.br.ead.serviceregitry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //para abilitar o servidor
public class ServiceRegitryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegitryApplication.class, args);
	}

}
