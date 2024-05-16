package br.gov.ce.pge.mspgeapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsPgeApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPgeApiGatewayApplication.class, args);
	}

}
