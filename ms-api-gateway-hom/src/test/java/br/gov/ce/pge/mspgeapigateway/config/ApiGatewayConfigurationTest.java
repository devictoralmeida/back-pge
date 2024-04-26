package br.gov.ce.pge.mspgeapigateway.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ApiGatewayConfigurationTest {

	@InjectMocks
	private ApiGatewayConfiguration configuration;

	@Mock
	private RouteLocatorBuilder builder;

	@Mock
	private RouteLocatorBuilder.Builder routesBuilder;

	@Mock
	private JwtAuthorizationFilter jwtAuthorizationFilter;

	@Test
	void gateway_router_test() {
		ReflectionTestUtils.setField(configuration, "serviceOauth", "http://localhost:8080/oauth");
		ReflectionTestUtils.setField(configuration, "serviceUser", "http://localhost:8080/user");
		ReflectionTestUtils.setField(configuration, "serviceGestao", "http://localhost:8080/gestao");

		when(builder.routes()).thenReturn(routesBuilder);

		when(routesBuilder.route(any())).thenReturn(routesBuilder);

		RouteLocator routeLocator = mock(RouteLocator.class);

		when(routesBuilder.build()).thenReturn(routeLocator);

		RouteLocator result = configuration.gatewayRouter(builder);

		assertNotNull(result);

		verify(routesBuilder, times(37)).route(any());
		verify(routesBuilder, times(1)).build();
	}

	@Test
	void log_request_filter_test() {

		GlobalFilter result = configuration.logRequestFilter();

		assertNotNull(result);
	}
}
