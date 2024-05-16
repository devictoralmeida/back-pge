package br.gov.ce.pge.mspgeapigateway.config;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.mspgeapigateway.utils.JwtUtil;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<Config> {

	private JwtUtil jwtUtil;

	private static final Logger LOGGER = LogManager.getLogger(JwtAuthorizationFilter.class);

	public JwtAuthorizationFilter(JwtUtil jwtUtil) {
		super(Config.class);
		this.jwtUtil = jwtUtil;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			var jwt = exchange.getRequest().getHeaders().getFirst("Authorization");

			if (jwt == null || !jwt.startsWith("Bearer ")) {
				LOGGER.info("JWT não inicia com Bearer");
				return respostaNaoAutorizada(exchange);
			}

			try {
				String token = jwt.substring(7);
				
				ObjectMapper objectMapper = new ObjectMapper();
				String[] array = objectMapper.readValue(jwtUtil.validateToken(token).toString(), String[].class);
				
	            String nomeUsuario = jwtUtil.getNomeUsuarioByToken(token);

	            exchange.getRequest().mutate().header("nomeUsuario", nomeUsuario);
				
				List<String> permissoes = Arrays.asList(array);

				return verificarPermissoes(chain, exchange, permissoes, config);
			} catch (JsonProcessingException e) {
				LOGGER.info("ERROR JsonProcessingException >>>>>> " + e);
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}
		};
	}

	private Mono<Void> verificarPermissoes(GatewayFilterChain chain, ServerWebExchange exchange,
			List<String> permissoes, Config config) {

		if (permissoes.isEmpty()) {
			LOGGER.info("<<<<<<< PERMISSÕES VAZIA >>>>>> ");
			return respostaNaoAutorizada(exchange);
		} else if (!permissoes.contains(config.getIdentificador())) {
			LOGGER.info("<<<<<<< ERROR NÃO CONTEM PERMISSÕES >>>>>> " + config.getIdentificador());
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return respostaNaoAutorizada(exchange);
		}

		return chain.filter(exchange);
	}

	private Mono<Void> respostaNaoAutorizada(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}

}
