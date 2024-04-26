package br.gov.ce.pge.mspgeapigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import static br.gov.ce.pge.mspgeapigateway.constante.Rota.*;
import static br.gov.ce.pge.mspgeapigateway.constante.Identificador.*;
import static br.gov.ce.pge.mspgeapigateway.constante.Permissao.*;

@Configuration
public class ApiGatewayConfiguration {

	private static final String HISTORICO_PARAMETRO_ID = "/historico/{id}";
	private static final String PARAMETRO_ID = "/{id}";
	
	@Value("${ms.service.oauth}")
	private String serviceOauth;

	@Value("${ms.service.user}")
	private String serviceUser;

	@Value("${ms.service.gestao}")
	private String serviceGestao;

	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	
	public ApiGatewayConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter) {
		this.jwtAuthorizationFilter = jwtAuthorizationFilter;
	}

	@Bean
	RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path(ORIGEM_DEBITO + PARAMETRO_ID).and().method(HttpMethod.DELETE)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ORIGEM_DEBITO + EXCLUIR))))
						.uri(serviceGestao))
				.route(p -> p.path(ORIGEM_DEBITO + PARAMETRO_ID).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ORIGEM_DEBITO + EDITAR))))
						.uri(serviceGestao))
				.route(p -> p.path(ORIGEM_DEBITO).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ORIGEM_DEBITO + CADASTRAR))))
						.uri(serviceGestao))
				.route(p -> p.path(ORIGEM_DEBITO + HISTORICO_PARAMETRO_ID)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ORIGEM_DEBITO + HISTORICO))))
						.uri(serviceGestao))
				.route(p -> p.path(ORIGEM_DEBITO + "/**")
						.uri(serviceGestao))
				
				
				.route(p -> p.path(TIPO_RECEITA + PARAMETRO_ID).and().method(HttpMethod.DELETE)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_TIPO_RECEITA + EXCLUIR))))
						.uri(serviceGestao))
				.route(p -> p.path(TIPO_RECEITA + PARAMETRO_ID).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_TIPO_RECEITA + EDITAR))))
						.uri(serviceGestao))
				.route(p -> p.path(TIPO_RECEITA).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_TIPO_RECEITA + CADASTRAR))))
						.uri(serviceGestao))
				.route(p -> p.path(TIPO_RECEITA + HISTORICO_PARAMETRO_ID)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_TIPO_RECEITA + HISTORICO))))
						.uri(serviceGestao))
				.route(p -> p.path(TIPO_RECEITA + "/**")
						.uri(serviceGestao))
				
				.route(p -> p.path(PRODUTO_SERVICO + PARAMETRO_ID).and().method(HttpMethod.DELETE)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PRODUTO_SERVICO + EXCLUIR))))
						.uri(serviceGestao))
				.route(p -> p.path(PRODUTO_SERVICO + PARAMETRO_ID).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PRODUTO_SERVICO + EDITAR))))
						.uri(serviceGestao))
				.route(p -> p.path(PRODUTO_SERVICO).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PRODUTO_SERVICO + CADASTRAR))))
						.uri(serviceGestao))
				.route(p -> p.path(PRODUTO_SERVICO + HISTORICO_PARAMETRO_ID)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PRODUTO_SERVICO + HISTORICO))))
						.uri(serviceGestao))
				.route(p -> p.path(PRODUTO_SERVICO + "/**")
						.uri(serviceGestao))
				
				.route(p -> p.path(FASE_DIVIDA).uri(serviceGestao))
				
				.route(p -> p.path(PERMISSAO).uri(serviceUser))
				
				.route(p -> p.path(MODULO).uri(serviceUser))
				
				.route(p -> p.path(SISTEMA).uri(serviceUser))
				
				
				.route(p -> p.path(PERFIL_ACESSO).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PERFIL_ACESSO + CADASTRAR))))
						.uri(serviceUser))
				.route(p -> p.path(PERFIL_ACESSO).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PERFIL_ACESSO + EDITAR))))
						.uri(serviceUser))
				.route(p -> p.path(PERFIL_ACESSO + HISTORICO_PARAMETRO_ID)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PERFIL_ACESSO + HISTORICO))))
						.uri(serviceUser))
				.route(p -> p.path(PERFIL_ACESSO + PARAMETRO_ID).and().method(HttpMethod.DELETE)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PERFIL_ACESSO + EXCLUIR))))
						.uri(serviceUser))
				.route(p -> p.path(PERFIL_ACESSO + "/**").uri(serviceUser))
				
				
				.route(p -> p.path(CONDICAO_ACESSO).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_CONDICOES_ACESSO + CADASTRAR))))
						.uri(serviceUser))
				.route(p -> p.path(CONDICAO_ACESSO).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_CONDICOES_ACESSO + CADASTRAR))))
						.uri(serviceUser))
				.route(p -> p.path(CONDICAO_ACESSO + "/**").uri(serviceUser))
				
				
				.route(p -> p.path(USUARIO).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_USUARIO + CADASTRAR))))
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + PARAMETRO_ID).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_USUARIO + EDITAR))))
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + PARAMETRO_ID).and().method(HttpMethod.DELETE)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_USUARIO + EXCLUIR))))
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + HISTORICO_PARAMETRO_ID)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_USUARIO + HISTORICO))))
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + "/**").uri(serviceUser))
				
				.route(p -> p.path(LOGIN).uri(serviceOauth))
				
				.route(p -> p.path(TERMOS_CONDICOES + PARAMETRO_ID).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_TERMOS_CONDICOES + EDITAR))))
						.uri(serviceUser))
				.route(p -> p.path(TERMOS_CONDICOES + PARAMETRO_ID).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_TERMOS_CONDICOES + EDITAR))))
						.uri(serviceUser))
				.route(p -> p.path(TERMOS_CONDICOES + HISTORICO_PARAMETRO_ID).and().method(HttpMethod.GET)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_TERMOS_CONDICOES + HISTORICO))))
						.uri(serviceUser))
				.route(p -> p.path(TERMOS_CONDICOES + "/**").uri(serviceUser))
				.build();
	}

	@Bean
	GlobalFilter logRequestFilter() {
		return (exchange, chain) -> chain.filter(exchange);
	}
}
