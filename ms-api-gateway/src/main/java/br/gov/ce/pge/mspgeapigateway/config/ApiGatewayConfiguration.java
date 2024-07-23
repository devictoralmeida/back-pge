package br.gov.ce.pge.mspgeapigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import static br.gov.ce.pge.mspgeapigateway.constante.Identificador.*;
import static br.gov.ce.pge.mspgeapigateway.constante.Permissao.*;
import static br.gov.ce.pge.mspgeapigateway.constante.Rota.*;

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

	@Value("${ms.service.inscricao}")
	private String serviceInscricao;

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
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
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
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
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
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceGestao))

				.route(p -> p.path(FASE_DIVIDA + PARAMETRO_ID).and().method(HttpMethod.DELETE)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_FASE_DIVIDA + EXCLUIR))))
						.uri(serviceGestao))
				.route(p -> p.path(FASE_DIVIDA + PARAMETRO_ID).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_FASE_DIVIDA + EDITAR))))
						.uri(serviceGestao))
				.route(p -> p.path(FASE_DIVIDA).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_FASE_DIVIDA + CADASTRAR))))
						.uri(serviceGestao))
				.route(p -> p.path(FASE_DIVIDA + HISTORICO_PARAMETRO_ID)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_FASE_DIVIDA + HISTORICO))))
						.uri(serviceGestao))
				.route(p -> p.path(FASE_DIVIDA + "/**")
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceGestao))

						.route(p -> p.path(PERMISSAO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceUser))

						.route(p -> p.path(MODULO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceUser))

						.route(p -> p.path(SISTEMA)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceUser))

						.route(p -> p.path(AREA)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceUser))


						.route(p -> p.path(PERFIL_ACESSO).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PERFIL_ACESSO + CADASTRAR))))
						.uri(serviceUser))
				.route(p -> p.path(PERFIL_ACESSO + PARAMETRO_ID).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PERFIL_ACESSO + EDITAR))))
						.uri(serviceUser))
				.route(p -> p.path(PERFIL_ACESSO + HISTORICO_PARAMETRO_ID)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PERFIL_ACESSO + HISTORICO))))
						.uri(serviceUser))
				.route(p -> p.path(PERFIL_ACESSO + PARAMETRO_ID).and().method(HttpMethod.DELETE)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_PERFIL_ACESSO + EXCLUIR))))
						.uri(serviceUser))
				.route(p -> p.path(PERFIL_ACESSO + "/**")
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceUser))


						.route(p -> p.path(CONDICAO_ACESSO).and().method(HttpMethod.POST)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_CONDICOES_ACESSO + CADASTRAR))))
						.uri(serviceUser))
				.route(p -> p.path(CONDICAO_ACESSO).and().method(HttpMethod.PUT)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_CONDICOES_ACESSO + CADASTRAR))))
						.uri(serviceUser))
				.route(p -> p.path(CONDICAO_ACESSO + "/**")
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceUser))


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
				.route(p -> p.path(USUARIO + "/{email}/email-recuperacao/{nomeSistema}")
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + PARAMETRO_ID + "/recuperacao-senha/{token}")
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + PARAMETRO_ID + "/senha/{token}")
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + PARAMETRO_ID +"/email-alteracao/{nomeSistema}")
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + PARAMETRO_ID + "/validacao-codigo/{codigo}")
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + PARAMETRO_ID + "/alteracao-senha/")
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + PARAMETRO_ID).and().method(HttpMethod.GET)
						.uri(serviceUser))
				.route(p -> p.path(USUARIO + "/**")
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceUser))

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
				.route(p -> p.path(TERMOS_CONDICOES + "/**")
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceUser))

						.route(p -> p.path(INSCRICAO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(LIVRO_ELETRONICO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))

						.route(p -> p.path(DIVIDA + "/**")
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))

						.route(p -> p.path(QUALIFICACAO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(STATUS_DEBITO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(TIPO_CONTATO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(TIPO_DEVEDOR)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(TIPO_DOCUMENTO_ANEXO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(TIPO_DOCUMENTO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(TIPO_PAPEL_PESSOA)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(TIPO_PESSOA)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(TIPO_PROCESSO)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(VARA_ORIGEM)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))
				.route(p -> p.path(PESSOA + "/**")
										.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
										.uri(serviceInscricao))
				.route(p -> p.path(ACAO_JUDICIAL)
								.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
								.uri(serviceInscricao))
				.route(p -> p.path(ACAO_JUDICIAL + PARAMETRO_ID)
						.filters(f -> f.filter(jwtAuthorizationFilter.apply(new Config(IDF_ROTAS_PERMITIDAS))))
						.uri(serviceInscricao))

						.build();
	}

	@Bean
	GlobalFilter logRequestFilter() {
		return (exchange, chain) -> chain.filter(exchange);
	}
}
