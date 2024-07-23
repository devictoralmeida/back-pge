package br.gov.ce.pge.gestao.config;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .slidingWindowSize(SharedConstant.TAMANHO_JANELA)
                .failureRateThreshold(SharedConstant.TAXA_FALHA)
                .minimumNumberOfCalls(SharedConstant.NUMERO_MINIMO_CHAMADAS)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                .waitDurationInOpenState(Duration.ofSeconds(SharedConstant.DURACAO_ABRIR_ESTADO))
                .permittedNumberOfCallsInHalfOpenState(SharedConstant.NUMERO_PERMITIDOS_CHAMADAS)
                .build();
    }
}

