package br.gov.ce.pge.mspgeapigateway.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({ MockitoExtension.class })
class HealthCheckControllerTest {

	@InjectMocks
	HealthCheckController controller;

	@Test
	public void test_health_check() {
		assertNotNull(controller.healthCheck());
	}

}
