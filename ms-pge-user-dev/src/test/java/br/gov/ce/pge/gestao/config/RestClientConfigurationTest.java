package br.gov.ce.pge.gestao.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestClientConfigurationTest {

    @Test
    void test_get_restTemplate() { 
        RestClientConfiguration restClientConfiguration = new RestClientConfiguration();
        RestTemplate restTemplate = restClientConfiguration.getRestTemplate();

        assertNotNull(restTemplate);
    }

    @Test
    void test_rest_templateBuilder() {
        RestClientConfiguration restClientConfiguration = new RestClientConfiguration();
        RestTemplate restTemplate = restClientConfiguration.restTemplate(new RestTemplateBuilder());

        assertNotNull(restTemplate);
    }

}
