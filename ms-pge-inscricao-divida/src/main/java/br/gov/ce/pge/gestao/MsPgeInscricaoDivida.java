package br.gov.ce.pge.gestao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(title = "API",version = "1.0",description = "Documentação da API"),
		servers = {@io.swagger.v3.oas.annotations.servers.Server(url = "/",description = "API")}
)
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@EnableJpaAuditing
@EnableJpaRepositories(basePackages="br.gov.ce.pge.gestao", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@SpringBootApplication
public class MsPgeInscricaoDivida {

	public static void main(String[] args) {
		SpringApplication.run(MsPgeInscricaoDivida.class, args);
	}

}
