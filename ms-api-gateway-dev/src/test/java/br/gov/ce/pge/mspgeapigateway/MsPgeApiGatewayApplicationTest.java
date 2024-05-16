package br.gov.ce.pge.mspgeapigateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = MsPgeApiGatewayApplication.class)
@TestPropertySource(properties = { "ms.service.oauth=http://localhost:8080/oauth",
		"ms.service.user=http://localhost:8080/user", "ms.service.gestao=http://localhost:8080/gestao",
		"eureka.client.service-url.defaultZone=http://localhost:8761/eureka",
		"api.secutiry.token.secret=your-secret-key","ms.service.inscricao=http://localhost:8080/oauth" })
class MsPgeApiGatewayApplicationTest {

	@Test
	void teste_context_loads() {
	}

}
