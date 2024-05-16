package br.gov.ce.pge.gestao.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

class CircuitBreakerConfigurationTest {

	@Test
	public void testCircuitBreakerConfig() {
		CircuitBreakerConfiguration configuration = new CircuitBreakerConfiguration();
		CircuitBreakerConfig config = configuration.circuitBreakerConfig();

		assertThat(config.getSlidingWindowSize()).isEqualTo(30);
		assertThat(config.getFailureRateThreshold()).isEqualTo(80.0f);
		assertThat(config.getMinimumNumberOfCalls()).isEqualTo(10);
		assertThat(config.getSlidingWindowType()).isEqualTo(CircuitBreakerConfig.SlidingWindowType.TIME_BASED);
		assertThat(config.getPermittedNumberOfCallsInHalfOpenState()).isEqualTo(5);
	}

}
