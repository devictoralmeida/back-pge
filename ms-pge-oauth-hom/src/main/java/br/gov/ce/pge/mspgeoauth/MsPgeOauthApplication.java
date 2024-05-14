package br.gov.ce.pge.mspgeoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class MsPgeOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPgeOauthApplication.class, args);
	}

}
