package br.gov.ce.pge.gestao.config;

import java.io.InputStream;
import java.util.Properties;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

public class PropertiesConfig {

	private static final String VAZIO = "";
    private PropertiesConfig() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    private static Properties loadProperties(){

        Properties properties = new Properties();

        try(InputStream inputStream = PropertiesConfig.class.getResourceAsStream(SharedConstant.PROPERTIES)) {

            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
           throw new NegocioException("Erro ao carregar configurações " + e);
        }
    }

    public static String getProperty(String property) {
    	Properties properties = PropertiesConfig.loadProperties();
    	return properties.getProperty(property, VAZIO);
    }
}