package br.gov.ce.pge.gestao.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.client.OrigemDebitoFeingClient;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import feign.FeignException.FeignClientException;

@Service
public class OrigemDebitoServiceImpl implements OrigemDebitoService {

	@Autowired
	private OrigemDebitoFeingClient atributosDividaFeingClient;

	@Override
	public OrigemDebitoResponseDto findById(UUID id) {
		try {
			var response = atributosDividaFeingClient.findById(id);
			return response.getBody().getData();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				throw new NegocioException(getMessageErro(e.getMessage()));
			}

			throw new NegocioException(e.getMessage());
		}
	}

	private String getMessageErro(String errorMessage) {
		int startIndexOfMessage = errorMessage.indexOf("mensagem\":\"") + "mensagem\":\"".length();
		int endIndexOfMessage = errorMessage.indexOf("\"", startIndexOfMessage);
		return errorMessage.substring(startIndexOfMessage, endIndexOfMessage);
	}

}
