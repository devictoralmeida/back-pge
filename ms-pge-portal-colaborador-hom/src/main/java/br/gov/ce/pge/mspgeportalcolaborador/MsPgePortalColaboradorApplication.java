package br.gov.ce.pge.mspgeportalcolaborador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@EnableJpaRepositories(basePackages="br.gov.ce.pge.mspgeportalcolaborador")
@SpringBootApplication
public class MsPgePortalColaboradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPgePortalColaboradorApplication.class, args);
	}

}
