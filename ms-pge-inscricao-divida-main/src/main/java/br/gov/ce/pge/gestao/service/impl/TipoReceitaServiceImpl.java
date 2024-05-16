package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.client.TipoReceitaFeingClient;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import feign.FeignException.FeignClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

	String getMessageErro(String errorMessage) {
		int startIndexOfMessage = errorMessage.indexOf("mensagem\":\"") + "mensagem\":\"".length();
		int endIndexOfMessage = errorMessage.indexOf("\"", startIndexOfMessage);
		if (endIndexOfMessage == -1) {
			return errorMessage;
		}
		return errorMessage.substring(startIndexOfMessage, endIndexOfMessage);
	}

}
