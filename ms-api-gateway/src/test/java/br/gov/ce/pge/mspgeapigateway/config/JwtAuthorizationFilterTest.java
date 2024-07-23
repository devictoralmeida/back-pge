package br.gov.ce.pge.mspgeapigateway.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import br.gov.ce.pge.mspgeapigateway.constante.Identificador;
import br.gov.ce.pge.mspgeapigateway.service.RedisService;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.mspgeapigateway.utils.JwtUtil;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class JwtAuthorizationFilterTest {

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private JwtAuthorizationFilter filter;

	@Mock
	private GatewayFilterChain chain;

	@Mock
	private RedisService redisService;

	@Test
	void test_apply_when_authorization_header_not_present() {

		Config config = new Config("admin");
		ServerWebExchange exchange = createServerWebExchange(null);

		filter.apply(config).filter(exchange, chain);

		assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
		verify(chain, never()).filter(exchange);
	}

	@Test
	void test_apply_when_authorization_header_present_but_invalid() {

		Config config = new Config("admin");
		ServerWebExchange exchange = createServerWebExchange("InvalidToken");

		filter.apply(config).filter(exchange, chain);

		assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
		verify(chain, never()).filter(exchange);
	}

	private ServerWebExchange createServerWebExchange(String authorizationHeader) {
		MockServerHttpRequest request = MockServerHttpRequest.get("/")
				.header(HttpHeaders.AUTHORIZATION, authorizationHeader).build();
		return MockServerWebExchange.from(request);
	}

	@Test
	void test_apply_when_authorization_header_valid_token_but_no_permissions() {

		Config config = new Config("admin");

		ServerWebExchange exchange = createServerWebExchange("Bearer validToken");
		when(jwtUtil.validateToken("validToken")).thenReturn("");

		filter.apply(config).filter(exchange, chain);

		assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
		verify(chain, never()).filter(exchange);
	}

	@Test
	void test_apply_when_authorization_header_valid_token_with_permissions() throws JsonProcessingException {
		Config config = new Config("admin");
		ServerWebExchange exchange = createServerWebExchange("Bearer validToken");

		when(redisService.getUserIp(any())).thenReturn(Arrays.asList(new Document("ip", "127.0.0.1").append("token", "validToken")));

		List<String> permissions = Arrays.asList("read", "write", "delete");
		ObjectMapper objectMapper = new ObjectMapper();
		String claimsJson = objectMapper.writeValueAsString(permissions);
		when(jwtUtil.validateToken("validToken")).thenReturn(claimsJson);

		Mono<Void> result = filter.apply(config).filter(exchange, chain).then();

		StepVerifier.create(result).expectComplete().verify();

		assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
	}

	@Test
	void teste_permissoes_vazias() throws JsonProcessingException {
		Config config = new Config("admin");
		ServerWebExchange exchange = createServerWebExchange("Bearer validToken");

		when(redisService.getUserIp(any())).thenReturn(Arrays.asList(new Document("ip", "127.0.0.1").append("token", "validToken")));

		List<String> permissions = Arrays.asList();
		ObjectMapper objectMapper = new ObjectMapper();
		String claimsJson = objectMapper.writeValueAsString(permissions);
		when(jwtUtil.validateToken("validToken")).thenReturn(claimsJson);

		Mono<Void> result = filter.apply(config).filter(exchange, chain).then();

		StepVerifier.create(result).expectComplete().verify();

		assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
	}

	@Test
	void teste_cache_invalido() throws JsonProcessingException {
		Config config = new Config("admin");
		ServerWebExchange exchange = createServerWebExchange("Bearer validToken");

		List<String> permissions = Arrays.asList();
		ObjectMapper objectMapper = new ObjectMapper();
		String claimsJson = objectMapper.writeValueAsString(permissions);
		when(jwtUtil.validateToken("validToken")).thenReturn(claimsJson);

		Mono<Void> result = filter.apply(config).filter(exchange, chain).then();

		StepVerifier.create(result).expectComplete().verify();

		assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
	}


}
