package br.gov.ce.pge.mspgeapigateway.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.gov.ce.pge.mspgeapigateway.constante.Identificador;
import br.gov.ce.pge.mspgeapigateway.service.RedisService;
import br.gov.ce.pge.mspgeapigateway.utils.DispositivoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
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

	private final RedisService redisService;

	private final JwtUtil jwtUtil;

	private static final Logger LOGGER = LogManager.getLogger(JwtAuthorizationFilter.class);

	public JwtAuthorizationFilter(JwtUtil jwtUtil, RedisService redisService) {
		super(Config.class);
		this.jwtUtil = jwtUtil;
		this.redisService = redisService;
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

				String detalhesDispositivo = DispositivoUtil.getDetalhesDispositivo(exchange.getRequest().getHeaders().getFirst("user-agent"));

				if(!validarTokenCache(token, nomeUsuario, DispositivoUtil.extrairIp(exchange) + " - " + detalhesDispositivo)) {
					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
					return exchange.getResponse().setComplete();
				}

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

	private boolean validarTokenCache(String token, String nomeUsuario, String ip) {
		List<Document> cache = this.redisService.getUserIp(nomeUsuario);

		if (cache == null) {
			LOGGER.info("Cache é null para usuário: {}", nomeUsuario);
			return false;
		}

		LOGGER.info("userIp: {}", cache);
		LOGGER.info("Ip requisição: {}", ip);

		List<Document> filtrado = cache.stream().filter(filter -> filter.getString("token").equals(token)).collect(Collectors.toList());

		return !filtrado.isEmpty();
	}

	private Mono<Void> verificarPermissoes(GatewayFilterChain chain, ServerWebExchange exchange,
			List<String> permissoes, Config config) {

		if(Identificador.IDF_ROTAS_PERMITIDAS.equals(config.getIdentificador())) {
			return chain.filter(exchange);
		}

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
