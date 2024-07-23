package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.client.ProdutoServicoFeingClient;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;
import br.gov.ce.pge.gestao.service.ProdutoServicoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import feign.FeignException.FeignClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class ProdutoServicoServiceImpl implements ProdutoServicoService {
    private final ProdutoServicoFeingClient produtoServicoFeingClient;

    public ProdutoServicoServiceImpl(ProdutoServicoFeingClient produtoServicoFeingClient) {
        this.produtoServicoFeingClient = produtoServicoFeingClient;
    }

    @Override
    public ProdutoServicoResponseDto findById(UUID id) {
        try {
            ResponseEntity<ResponseClientDto<ProdutoServicoResponseDto>> response = produtoServicoFeingClient.findById(id);
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (FeignClientException e) {
            if (e.status() == SharedConstant.STATUS_NOT_FOUND || e.status() == SharedConstant.STATUS_BAD_REQUEST) {
                throw new NegocioException(getMessageErro(e.getMessage()));
            }

            throw new NegocioException(e.getMessage());
        }
    }

    String getMessageErro(String errorMessage) {
        int startIndexOfMessage = errorMessage.indexOf("mensagem\":\"") + "mensagem\":\"".length();
        int endIndexOfMessage = errorMessage.indexOf("\"", startIndexOfMessage);
        if (endIndexOfMessage == SharedConstant.END_INDEX_MESSAGE) {
            return errorMessage;
        }
        return errorMessage.substring(startIndexOfMessage, endIndexOfMessage);
    }

}
