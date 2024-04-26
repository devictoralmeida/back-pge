package br.gov.ce.pge.gestao.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.client.TipoReceitaFeingClient;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import feign.FeignException.FeignClientException;

@Service
public class TipoReceitaServiceImpl implements TipoReceitaService {

	@Autowired
	private TipoReceitaFeingClient tipoReceitaFeingClient;

	@Override
	public TipoReceitaResponseDto findById(UUID id) {
		try {
			var response = tipoReceitaFeingClient.findById(id);
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
